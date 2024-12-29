package com.example.pertemuan12.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.model.Mahasiswa
import com.example.pertemuan12.navigation.DestinasiNavigasi
import com.example.pertemuan12.ui.viewmodel.HomeUiState
import com.example.pertemuan12.ui.viewmodel.HomeViewModel
import com.example.pertemuan12.ui.viewmodel.PenyediaViewModel

object HomeView : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Mhs"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // Adjusted for possible missing CostumeTopAppBar definition
            TopAppBar(
                title = { Text(text = HomeView.titleRes) },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { viewModel.getMhs() }) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_popup_sync),
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToltemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_input_add),
                    contentDescription = "Add Kontak"
                )
            }
        },
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.mhsUIState,
            retryAction = { viewModel.getMhs() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteMhs(it.nim)
                viewModel.getMhs()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success ->
            if (homeUiState.mahasiswa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kontak")
                }
            } else {
                MhsLayout(
                    mahasiswa = homeUiState.mahasiswa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = onDetailClick,
                    onDeleteClick = onDeleteClick
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.images),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun MhsLayout(
    mahasiswa: List<Mahasiswa>,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Mahasiswa) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(mahasiswa) { kontak ->
            MhsCard(
                mahasiswa = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kontak.nim) },
                onDeleteClick = { onDeleteClick(kontak) }
            )
        }
    }
}

@Composable
fun MhsCard(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mahasiswa.nama,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(mahasiswa) }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_delete),
                        contentDescription = null,
                    )
                }
            }
            Text(
                text = mahasiswa.nim,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = mahasiswa.kelas,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = mahasiswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
