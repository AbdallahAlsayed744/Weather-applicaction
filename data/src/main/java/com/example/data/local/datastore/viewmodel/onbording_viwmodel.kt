package com.example.data.local.datastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.datastore.myappsaveorread
import com.example.data.local.datastore.on_obordingevent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class onbording_viwmodel @Inject constructor(
    private val myappsaveorread: myappsaveorread
) : ViewModel() {

    fun myevent(event: on_obordingevent) {
        when (event) {
            is on_obordingevent.saveappentry -> {
                saveappentry()
            }
        }
    }

    private fun saveappentry() {
        viewModelScope.launch {
            myappsaveorread.saveentry()
        }

    }

}