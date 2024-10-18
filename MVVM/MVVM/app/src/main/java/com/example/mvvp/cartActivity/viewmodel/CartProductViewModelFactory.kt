package com.example.mvvp.cartActivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvp.model.ProductRepo

class CartProductViewModelFactory (private val productRepo: ProductRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartProductViewModel(productRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}