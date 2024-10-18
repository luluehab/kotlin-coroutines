package com.example.downloadworker.model

class Root(
    var products: List<Product>? = null,
    var total: Int = 0,
    var skip: Int = 0,
    var limit: Int = 0
)
