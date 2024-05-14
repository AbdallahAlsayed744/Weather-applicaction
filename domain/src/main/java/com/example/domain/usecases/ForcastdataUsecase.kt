package com.example.domain.usecases

import com.example.domain.repo.Forcastdatarepo

class ForcastdataUsecase(private val forcastdatarepo: Forcastdatarepo) {

    suspend operator fun invoke(key:String,q:String)=forcastdatarepo.getforcastdata(key, q)

}