package com.example.hangman.navigation

sealed class AppScreens(val route: String){
    object Login: AppScreens("login")
    object Menu: AppScreens("menu")
    object Game: AppScreens("game")
    object SpashScreen: AppScreens("splash_screen")
    object FinalScreen: AppScreens("final_screen")
}
