package com.sumeyyesahin.firebasekotlin

import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product

object Singleton { // anlamı: tek örnek (nesne) (Singleton) //nerelerde kullaılır? : 1-Veritabanı bağlantıları 2-Loglama 3-Çok fazla kaynak tüketen nesneler
    var chosenProduct : Product? = null // anlamı: seçilen ürün
    var allProducts: ArrayList<Product>? = null // anlamı: tüm ürünler
    var favoriteProduct: ArrayList<Product>? = null // anlamı: favori ürünler
   // var favSize: Int = 0
}