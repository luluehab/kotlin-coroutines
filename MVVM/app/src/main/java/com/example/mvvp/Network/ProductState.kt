package com.example.mvvp.Network

import com.example.downloadworker.model.Product


sealed class ProductState {
    object Loading : ProductState()
    data class Success(val products: List<Product>) : ProductState()
    data class Error(val message: String) : ProductState()
}