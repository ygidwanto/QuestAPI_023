package com.example.pertemuan12.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan12.ui.view.DestinasiEntry
import com.example.pertemuan12.ui.view.HomeView
import com.example.pertemuan12.ui.view.EntryMhsScreen
import com.example.pertemuan12.ui.view.HomeScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = HomeView.route,
        modifier = Modifier,
    ) {

        composable(HomeView.route) {
            HomeScreen(
                navigateToltemEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                onDetailClick = {

                }
            )
        }


        composable(DestinasiEntry.route) {
            EntryMhsScreen(
                navigateBack = {
                    navController.navigate(HomeView.route) {
                        popUpTo(HomeView.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
