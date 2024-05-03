package com.example.data.local.datastore

import com.example.domain.datastoreinterface.LocalManger
import kotlinx.coroutines.flow.Flow

class readentry(
    private val localmanager: LocalManger
) {
    operator fun invoke(): Flow<Boolean> {
        return localmanager.readentry()
    }
}