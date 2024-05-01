package com.example.weatherapp.ui.composables

sealed class Scree(val route: String) {
    object Splash : Scree("splash_screen")
    object Home : Scree("home_screen")

}