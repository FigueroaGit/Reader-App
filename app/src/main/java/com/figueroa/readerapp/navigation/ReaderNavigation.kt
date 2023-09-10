package com.figueroa.readerapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.figueroa.readerapp.screens.ReaderSplashScreen
import com.figueroa.readerapp.screens.details.BookDetailsScreen
import com.figueroa.readerapp.screens.home.Home
import com.figueroa.readerapp.screens.home.HomeScreenViewModel
import com.figueroa.readerapp.screens.login.ReaderLoginScreen
import com.figueroa.readerapp.screens.search.BookSearchViewModel
import com.figueroa.readerapp.screens.search.SearchScreen
import com.figueroa.readerapp.screens.stats.ReaderStatsScreen
import com.figueroa.readerapp.screens.update.BookUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(route = ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }

        composable(route = ReaderScreens.ReaderHomeScreen.name) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            Home(navController = navController, viewModel = homeScreenViewModel)
        }

        val detailName = ReaderScreens.DetailsScreen.name

        composable(
            route = "$detailName/{bookId}",
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }

        composable(route = ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }

        composable(route = ReaderScreens.SearchScreen.name) {
            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            SearchScreen(navController = navController, searchViewModel)
        }

        composable(route = ReaderScreens.ReaderStatsScreen.name) {
            ReaderStatsScreen(navController = navController)
        }

        val updateName = ReaderScreens.UpdateScreen.name
        composable(
            route = "$updateName/{bookItemId}",
            arguments = listOf(
                navArgument("bookItemId") {
                    type = NavType.StringType
                },
            ),
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItemId = it.toString())
            }
        }
    }
}
