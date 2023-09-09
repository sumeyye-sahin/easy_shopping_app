package com.sumeyyesahin.retrofitkotlintekrartekrar.models

import java.io.Serializable

data class Product ( // anlamı: ürün verileri (nesneleri aktarabilmek için)
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String,
    var isFavorite: Boolean=false
) : Serializable // anlamı: serileştirilebilir yap (nesneleri aktarabilmek için)