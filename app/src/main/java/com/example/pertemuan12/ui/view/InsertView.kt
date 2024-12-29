package com.example.pertemuan12.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.navigation.DestinasiNavigasi
import com.example.pertemuan12.ui.viewmodel.InsertViewModel
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
}
