package com.example.pertemuan12.ui.view

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.navigation.DestinasiNavigasi
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
