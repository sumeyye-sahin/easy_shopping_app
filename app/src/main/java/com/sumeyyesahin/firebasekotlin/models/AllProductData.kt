package com.sumeyyesahin.retrofitkotlintekrartekrar.models

import java.io.Serializable

data class AllProductData(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
) : Serializable