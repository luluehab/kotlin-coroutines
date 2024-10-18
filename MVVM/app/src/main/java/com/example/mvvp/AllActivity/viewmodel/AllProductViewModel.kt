package com.example.mvvp.AllActivity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.downloadworker.model.Product
import com.example.mvvp.Network.ProductState
import com.example.mvvp.model.ProductRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AllProductViewModel (private val repo: ProductRepo) : ViewModel() {

    private val _products = MutableStateFlow<ProductState>(ProductState.Loading)
    val products: StateFlow<ProductState> get() = _products

    // LiveData to expose errors
    private val _error = MutableStateFlow<String?>(null )
    val error: StateFlow<String?> get() = _error

    init {
        fetchProductsFromNetwork()
    }

     fun fetchProductsFromNetwork() {
         _products.value = ProductState.Loading
        viewModelScope.launch {
            try {
                val response = repo.getAllProducts(). first()// This returns Response<ProductsResponse>
                _products.value = ProductState.Success(response)  // Assuming ProductsResponse has a 'products' property
            }
            catch (e: Exception){
                _products.value = ProductState.Error("Error fetching products: ${e.message}")
                _error.value = "Error fetching products: ${e.message}"
            }
        }
    }

    fun addProductToFavorites(product: Product) {
        viewModelScope.launch {
            Log.i("TAG", "addProductToFavorites: ")
            repo.insertProduct(product)
        }
    }

}