package com.example.hangman.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hangman.screens.FinalScreen
import com.example.hangman.screens.Game
import com.example.hangman.screens.Login
import com.example.hangman.screens.Menu
import com.example.hangman.screens.SplashScreen

@Composable
fun AppNavigation(){
val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SpashScreen.route){
        composable(route = AppScreens.Login.route){
            Login(navController)
        }
        composable(route = AppScreens.Menu.route){
            Menu(navController)
        }
        composable(route = AppScreens.Game.route + "/{difficulty}"
            , arguments = listOf(navArgument(name = "difficulty"){
            type = NavType.StringType
        })){
            Game(navController, it.arguments?.getString("difficulty"))
        }
        composable(route = AppScreens.SpashScreen.route){
            SplashScreen(navController)
        }
        composable(
            route = AppScreens.FinalScreen.route + "/{result}/{difficulty}/{attempts}",
            arguments = listOf(
                navArgument(name = "result") {
                    type = NavType.StringType
                },
                navArgument(name = "difficulty") {
                    type = NavType.StringType
                },
                navArgument(name = "attempts") {
                    type = NavType.StringType
                }
            )
        ) {
            FinalScreen(
                navController,
                it.arguments?.getString("result"),
                it.arguments?.getString("difficulty"),
                it.arguments?.getString("attempts")
            )
        }

    }
}