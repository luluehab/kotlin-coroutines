package com.example.mvvp.model

import com.example.downloadworker.model.Product
import com.example.mvvp.Network.RemoteSource
import com.example.mvvp.database.LocalSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepo (private val remoteSource: RemoteSource, private val localSource : LocalSource)
{
    suspend fun getAllProducts(): List<Product> = withContext(Dispatchers.IO){
        val responce = remoteSource.getProducts()
        if(responce.isSuccessful)
        {
            responce.body()?.products ?: emptyList()
        }
        else{
            throw Exception("Failed to fetch products from remote")
        }
    }

    suspend fun insertProduct(product: Product) = withContext(Dispatchers.IO) {
        localSource.insertOneProduct(product)
    }

     suspend fun deleteProduct(product: Product) = withContext(Dispatchers.IO) {
         localSource.deleteProduct(product)
    }

    suspend fun getStoredProducts(): List<Product> {
        return localSource.getProducts()
    }

}