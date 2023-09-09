package com.sumeyyesahin.retrofitkotlintekrartekrar.data

import com.sumeyyesahin.retrofitkotlintekrartekrar.models.AllProductData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface { // anlamı: arayüz oluştur

    @GET("products") // anlamı: ürünleri al
    suspend fun getProducts(): Response<AllProductData> // anlamı: ürünleri al, tüm ürün verilerini al
}