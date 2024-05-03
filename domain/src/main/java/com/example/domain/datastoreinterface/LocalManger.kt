package com.example.domain.datastoreinterface

import kotlinx.coroutines.flow.Flow

interface LocalManger {
    suspend fun saveentry()

    fun readentry(): Flow<Boolean>
}