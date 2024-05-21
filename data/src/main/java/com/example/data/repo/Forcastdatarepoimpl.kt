package com.example.data.repo

import android.util.Log
import com.example.data.remote.Api
import com.example.domain.entity.my_Forcast
import com.example.domain.repo.Forcastdatarepo
import retrofit2.Response

class Forcastdatarepoimpl(
   private val api:Api
): Forcastdatarepo {



    override suspend fun getforcastdata(key:String,q:String): Response<my_Forcast> {
        val response = api.getforcastdata(key,q)
        if(response.isSuccessful&&response.body()!=null){
            Log.d("forcastresponse","Success")
            Log.d("forcastresponse",response.body().toString())
        }
        else{
            Log.d("forcastresponse","Fail")
            Log.d("forcastresponse",response.body().toString())
        }
        return response
    }

}