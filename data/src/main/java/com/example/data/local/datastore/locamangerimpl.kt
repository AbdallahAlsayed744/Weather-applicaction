package com.example.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.utilis.connstants.PREF_NAME
import com.example.data.utilis.connstants.key
import com.example.domain.datastoreinterface.LocalManger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class locamangerimpl(private val context: Context):LocalManger {
    override suspend fun saveentry() {
        context.datastore.edit {
                setting->
            setting[prefrenceskeys.app_key]=true
        }

    }

    override fun readentry(): Flow<Boolean> {
        return context.datastore.data.map {
                pref->
            pref[prefrenceskeys.app_key]?:false
        }
    }
}

private val Context.datastore : DataStore<Preferences> by preferencesDataStore(name =PREF_NAME )

private object prefrenceskeys{
    val app_key= booleanPreferencesKey(name = key)
}
