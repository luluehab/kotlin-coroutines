package com.example.mvvp.cartActivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.downloadworker.model.Product
import com.example.mvvp.model.ProductRepo
import kotlinx.coroutines.launch

class CartProductViewModel(private val repo: ProductRepo) : ViewModel() {

    private val _favoriteProducts = MutableLiveData<List<Product>>()
    val favoriteProducts: LiveData<List<Product>> get() = _favoriteProducts

    fun fetchFavoriteProducts() {
        viewModelScope.launch {
            try {
                repo.getStoredProducts().collect { products ->
                    _favoriteProducts.value = products
                }
            }catch (e: Exception)
            {

            }

        }
    }

    fun removeFavoriteProduct(product: Product) {
        viewModelScope.launch {
            repo.deleteProduct(product)
            fetchFavoriteProducts() // Refresh the list after deletion
        }
    }
}