package com.sumeyyesahin.retrofitkotlintekrartekrar.utils

import com.sumeyyesahin.retrofitkotlintekrartekrar.data.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance { // anlamı: Retrofit örneği (nesnesi)

    val api: ApiInterface by lazy {     // anlamı: api arayüzü (nesnesi) tembel (geçici) olarak oluştur
        Retrofit.Builder() // anlamı: Retrofit oluşturucu
            .baseUrl(Util.Base) // anlamı: temel url
            .addConverterFactory(GsonConverterFactory.create()) // anlamı: dönüştürücü ekle
            .build() // anlamı: oluştur
            .create(ApiInterface::class.java) // anlamı: Api arayüzü oluştur

    }
}