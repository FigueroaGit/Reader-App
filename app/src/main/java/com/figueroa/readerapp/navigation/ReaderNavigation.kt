package com.figueroa.readerapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.figueroa.readerapp.screens.ReaderSplashScreen
import com.figueroa.readerapp.screens.details.BookDetailsScreen
import com.figueroa.readerapp.screens.home.Home
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
            Home(navController = navController)
        }

        composable(route = ReaderScreens.DetailsScreen.name) {
            BookDetailsScreen(navController = navController)
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

        composable(route = ReaderScreens.UpdateScreen.name) {
            BookUpdateScreen(navController = navController)
        }
    }
}
