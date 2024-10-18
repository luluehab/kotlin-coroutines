package com.example.mvvp.cartActivity.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvp.Network.RemoteSource
import com.example.mvvp.R
import com.example.mvvp.cartActivity.viewmodel.CartProductViewModel
import com.example.mvvp.cartActivity.viewmodel.CartProductViewModelFactory
import com.example.mvvp.database.LocalSource
import com.example.mvvp.model.ProductRepo

class CartActivity : AppCompatActivity() {

    private lateinit var favViewModel: CartProductViewModel
    private lateinit var adapter: ListAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val remoteDataSource = RemoteSource
        val localDataSource = LocalSource(applicationContext)
        val productRepo = ProductRepo( remoteDataSource, localDataSource)
        favViewModel = CartProductViewModelFactory(productRepo).create(CartProductViewModel::class.java)

        adapter = ListAd { product ->

            favViewModel.removeFavoriteProduct(product)
            Toast.makeText(this, product.title + " Remove From Cart", Toast.LENGTH_SHORT).show()
            // communication.respond(it.title , it.category , it.description , it.img)
        }
        val recyclerView: RecyclerView = findViewById(R.id.allRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        favViewModel.favoriteProducts.observe(this) { products ->
            adapter.submitList(products)
        }

        favViewModel.fetchFavoriteProducts()
    }

}