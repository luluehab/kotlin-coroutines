package com.example.mvvp.database

import android.content.Context
import com.example.downloadworker.database.ProductDB
import com.example.downloadworker.database.ProductDao
import com.example.downloadworker.model.Product
import kotlinx.coroutines.flow.Flow


class LocalSource(context: Context) {

    private val productDAO: ProductDao = ProductDB.getInstance(context).GetProductDao()

    // Fetches products in a non-blocking way
    suspend fun getProducts() : Flow<List<Product>> {
        return productDAO.getProducts()  // Null safety can be handled here or upstream

    }

    // Inserts a list of products asynchronously
    suspend fun insert(products: List<Product>) {

            productDAO.insertProducts(products)

    }

    // Inserts a list of products asynchronously
    suspend fun insertOneProduct(product: Product) {
            productDAO.insertOneProduct(product)
    }

    // Deletes a product asynchronously

    suspend fun deleteProduct(product: Product) {
            productDAO.deleteProduct(product)

    }

    suspend fun getProductById(id: Int): Product? {
        return productDAO.getProductById(id)
    }


    companion object {
        @Volatile
        private var INSTANCE: LocalSource? = null

        fun getInstance(context: Context): LocalSource {
            return INSTANCE ?: synchronized(this) {
                val instance = LocalSource(context)
                INSTANCE = instance
                instance
            }
        }
    }
}