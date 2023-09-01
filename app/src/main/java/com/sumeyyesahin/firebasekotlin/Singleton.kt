package com.sumeyyesahin.firebasekotlin

import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product

object Singleton {
    var chosenProduct : Product? = null
    var allProducts: ArrayList<Product>? = null
    var favoriteProduct: ArrayList<Product>? = null
   // var favSize: Int = 0
}