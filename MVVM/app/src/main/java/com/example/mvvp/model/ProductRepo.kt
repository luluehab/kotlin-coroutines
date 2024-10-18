package com.example.mvvp.model

import android.util.Log
import com.example.downloadworker.model.Product
import com.example.mvvp.Network.RemoteSource
import com.example.mvvp.database.LocalSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ProductRepo (private val remoteSource: RemoteSource, private val localSource : LocalSource)
{
    suspend fun getAllProducts(): Flow<List<Product>> = flow{
        val responce = remoteSource.getProducts()
        emit(responce.body()?.products ?: emptyList())

    }.catch { exception ->
        emit(emptyList())
        Log.e("ProductFlow", "Error fetching products", exception)
    }

    suspend fun insertProduct(product: Product) = withContext(Dispatchers.IO) {
        localSource.insertOneProduct(product)
    }

     suspend fun deleteProduct(product: Product) = withContext(Dispatchers.IO) {
         localSource.deleteProduct(product)
    }

    suspend fun getStoredProducts(): Flow<List<Product>> {
        return localSource.getProducts()
    }

}