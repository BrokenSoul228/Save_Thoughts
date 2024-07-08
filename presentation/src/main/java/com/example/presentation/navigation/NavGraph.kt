package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.presentation.screens.ItemScreen
import com.example.presentation.screens.MainListScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home"){
            MainListScreen(navController)
        }

        composable(
            "item_screen/{state}/{title}/{mainText}/{itemId}",
            arguments = listOf(
                navArgument("state"){
                    type = NavType.StringType
                },
                navArgument("title"){
                    type = NavType.StringType
                },
                navArgument("mainText"){
                    type = NavType.StringType
                },
                navArgument("itemId"){
                    type = NavType.LongType
                }
            )
        )
        {
            val userState = it.arguments?.getString("state")
            val title = it.arguments?.getString("title")
            val mainText = it.arguments?.getString("mainText")
            val itemId = it.arguments?.getLong("itemId")
            ItemScreen(navController, userState!!, title!!, mainText!!, itemId!!)
        }
    }

}