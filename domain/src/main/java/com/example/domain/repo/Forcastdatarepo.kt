package com.example.domain.repo

import com.example.domain.entity.my_Forcast
import retrofit2.Response

interface Forcastdatarepo {

    suspend fun getforcastdata(key:String,q:String): Response<my_Forcast>

}