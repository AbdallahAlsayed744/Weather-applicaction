package com.example.data.local.datastore

import com.example.domain.datastoreinterface.LocalManger

class saveentry(private val localmanager: LocalManger) {


    suspend operator fun invoke() {
        localmanager.saveentry()
    }

}