package com.example.mvvp.AllActivity.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvp.AllActivity.viewmodel.AllProductViewModel
import com.example.mvvp.AllActivity.viewmodel.AllProductViewModelFactory
import com.example.mvvp.Network.RemoteSource
import com.example.mvvp.R
import com.example.mvvp.database.LocalSource
import com.example.mvvp.model.ProductRepo

class AllActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: ListAd
    private lateinit var viewModel: AllProductViewModel
    private lateinit var recyclerView: RecyclerView
    private val TAG: String = "track"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all)
        recyclerView = findViewById(R.id.cartRecyclerView)

        val remoteDataSource = RemoteSource
        val localDataSource = LocalSource(applicationContext)

        viewModel =  ViewModelProvider(
            this,
            AllProductViewModelFactory(ProductRepo(remoteDataSource, localDataSource))
        ).get(AllProductViewModel::class.java)


        recyclerAdapter = ListAd { product ->
            viewModel.addProductToFavorites(product)
            Toast.makeText(this,  product.title + " Added To Cart", Toast.LENGTH_SHORT).show()
            // communication.respond(it.title , it.category , it.description , it.img)
        }
        recyclerView.apply {
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@AllActivity).apply {
                orientation = RecyclerView.VERTICAL
            }
        }

        viewModel.products.observe(this, Observer { products ->
            recyclerAdapter.submitList(products)
        })

        viewModel.error.observe(this, Observer { errorMsg ->
            // Show error message to the user
        })
           //fetchProducts()
        viewModel.fetchProductsFromNetwork()
        }

    /*
    private fun fetchProducts() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.i(TAG, "onViewCreated: fetch from network ")
                // Call Retrofit using a coroutine
                val response = RetrofitHelper.retrofitInstance.getImage()
                withContext(Dispatchers.Main) {
                    Log.i(TAG, "fetchProducts: "+response.products.toString() )
                    recyclerAdapter.submitList(response.products)
                }

                // Save products to database
                withContext(Dispatchers.IO) {
                    response.products?.let {
                        ProductDB.getInstance(this@AllActivity).GetProductDao().insertProducts(
                            it
                        )
                    }



                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.i(TAG, "fetchProducts: Error fetching products: ${e.message}")
                    Toast.makeText(this@AllActivity, "Error fetching products: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
       }
    }*/


}