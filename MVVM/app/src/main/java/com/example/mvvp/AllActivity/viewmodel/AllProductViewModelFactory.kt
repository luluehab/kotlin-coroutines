package com.example.mvvp.AllActivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvp.model.ProductRepo

class AllProductViewModelFactory  (private val repo: ProductRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllProductViewModel::class.java)) {
            return AllProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}