package com.example.pertemuan12.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.navigation.DestinasiNavigasi
import com.example.pertemuan12.ui.viewmodel.InsertUiEvent
import com.example.pertemuan12.ui.viewmodel.InsertUiState
import com.example.pertemuan12.ui.viewmodel.InsertViewModel
import com.example.pertemuan12.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Mhs"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMhsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMhs()
                    navigateBack()
                }
            }
        )
    }
}
modifier = Modifier
.padding(innerPadding)
.verticalScroll(rememberScrollState())
.fillMaxWidth()

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onSiswaValueChange:(InsertUiEvent)-> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier= Modifier
){

}

