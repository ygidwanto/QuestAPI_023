package com.example.pertemuan12.ui.view

import android.graphics.drawable.Icon
import android.media.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import java.lang.reflect.Modifier

object HomeView : DestinasiNavigasi{
    override val route = "home"
    override val titleRes = "Home Mhs"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToltemEntry: ()-> Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(String)-> Unit={},
    viewModel: HomeViewModel = viewModel (factory = PenyediaViewModel.Factory)
){
    val scrollBehavior= TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title=DestinasiHome.titleRes,
                canNavigateBack=false,
                scrollBehavior=scrollBehavior,
                onRefrsh={
                    viewModel.getMhs()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToltemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icon.Default.Add, contentDescription = "Add Kontak")
            }
        },
    ){innerPadding->
        HomeStatus(
            homeUiState=viewModel.mhsUIState,
            retryAction={viewModel.getMhs()},modifier= Modifier.padding(innerPadding),
            onDetailClick=onDetailClick,onDeleteClick={
                viewModel.deleteMhs(it.nim )
                viewModel.getMhs()
            }
        )

    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction:()-> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick:(Mahasiswa)-> Unit={},
    onDetailClick: (String) -> Unit
){
    when(HomeUiState){
        is HomeUiState.Loading->OnLoading(modifier=modifier.fillMaxSize())

        is HomeUiState.Success ->
            if (homeUiState.mahasiswa.isEmpty()){
                return Box(modifier=modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Kontak")
                }
            }else{
                MhsLayout(

                    mahasiswa= homeUiState.mahasiswa, modifier=modifier.fillMaxWidth(),
                    onDetailClick={
                        onDetailClick(it)
                    }
                )
            }
        is HomeUiState.Error -> OnError(retryAction,modifier=modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: java.lang.reflect.Modifier= Modifier){
    Image(
        modifier=modifier.size(200.dp)
        painter= painterResource(R.drawable.images.jpg),
        contentDescription= stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier= Modifier){
    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter= painterResource(id=R.drawable.ic_connection_error),contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction){
            Text(stringResource(R.string.retry))
        }
    }
}