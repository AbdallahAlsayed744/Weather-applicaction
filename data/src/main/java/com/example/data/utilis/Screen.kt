package com.example.data.utilis

sealed class Scree(val route: String) {
    object Splash : Scree("splash_screen")

    object welcome_screen : Scree("welcome_screen")
    object Home : Scree("home_screen")

    object Searchscreen : Scree("search_screen")

}