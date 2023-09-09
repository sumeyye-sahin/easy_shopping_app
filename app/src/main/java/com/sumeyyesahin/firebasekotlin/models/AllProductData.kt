package com.sumeyyesahin.retrofitkotlintekrartekrar.models

import java.io.Serializable

data class AllProductData( // anlamı: tüm ürün verileri
    val limit: Int,
    val products: ArrayList<Product>,
    val skip: Int,
    val total: Int
) : Serializable // anlamı: serileştirilebilir yap (nesneleri aktarabilmek için)