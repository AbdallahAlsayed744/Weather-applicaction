package com.example.data.local.datastore.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.datastore.myappsaveorread
import com.example.data.utilis.Scree
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.time.delay
import java.time.Duration
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class datastoreviewmodel @Inject constructor(
    private val myappsaveorread: myappsaveorread
): ViewModel() {
    var splashcondition by mutableStateOf(true)
        private set

    var startdistination by mutableStateOf(Scree.welcome_screen.route)
        private set
    init {
        myappsaveorread.readentry().onEach {shouldstartfromhomescreen->
            if(shouldstartfromhomescreen){

                startdistination = Scree.Home.route
            }
            else {
                startdistination = Scree.welcome_screen.route
            }

            delay(Duration.ofSeconds(3))


            splashcondition=false



        }.launchIn(viewModelScope)
    }


}