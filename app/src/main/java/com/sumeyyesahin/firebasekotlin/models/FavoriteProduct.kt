package com.sumeyyesahin.firebasekotlin.models

data class FavoriteProduct( // anlamı: favori ürünler (nesneleri aktarabilmek için)
    var userId: String? = null,
    var productId: Int = 0
)

