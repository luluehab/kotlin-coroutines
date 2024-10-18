package com.example.downloadworker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class Product(
    @PrimaryKey
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var category: String? = null,
    var price: String? = null,
    var discountPercentage: Double = 0.0,
    var rating: String? = null,
    var stock: Int = 0,
    var brand: String? = null,
    var sku: String? = null,
    var weight: Int = 0,
    var warrantyInformation: String? = null,
    var shippingInformation: String? = null,
    var availabilityStatus: String? = null,
    var returnPolicy: String? = null,
    var minimumOrderQuantity: Int = 0,
    var thumbnail: String? = null
)
