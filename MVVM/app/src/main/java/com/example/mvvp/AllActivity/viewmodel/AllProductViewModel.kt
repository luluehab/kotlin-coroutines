package com.example.mvvp.AllActivity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.downloadworker.model.Product
import com.example.mvvp.model.ProductRepo
import kotlinx.coroutines.launch

class AllProductViewModel (private val repo: ProductRepo) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    // LiveData to expose errors
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchProductsFromNetwork()
    }

     fun fetchProductsFromNetwork() {
        viewModelScope.launch {
            try {
                val response = repo.getAllProducts()// This returns Response<ProductsResponse>
                _products.postValue(response) // Assuming ProductsResponse has a 'products' property
            }
            catch (e: Exception){
                _error.postValue("Error fetching products: ${e.message}")
            }
        }
    }

    fun addProductToFavorites(product: Product) {
        viewModelScope.launch {
            Log.i("TAG", "nahass addProductToFavorites: ")
            repo.insertProduct(product)
        }
    }

}