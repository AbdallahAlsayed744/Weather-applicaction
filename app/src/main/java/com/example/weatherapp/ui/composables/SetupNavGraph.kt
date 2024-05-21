package com.example.weatherapp.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.data.local.datastore.viewmodel.datastoreviewmodel
import com.example.data.local.datastore.viewmodel.onbording_viwmodel
import com.example.data.utilis.Scree

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Scree.Splash.route ){
        composable(route = Scree.Splash.route) {

            SplashScreen(navController)
        }
        composable(route = Scree.welcome_screen.route) {

                val model : onbording_viwmodel = hiltViewModel()
                WelcomeScreen(model::myevent,navController)
            }

        composable(route= Scree.Home.route){
            HomeScreen(
               navController
            )
        }
        composable(route = Scree.Searchscreen.route){
            SearchScreen(navController)
        }
    }
    }


