package com.example.mvvp.AllActivity.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvp.AllActivity.viewmodel.AllProductViewModel
import com.example.mvvp.AllActivity.viewmodel.AllProductViewModelFactory
import com.example.mvvp.Network.ProductState
import com.example.mvvp.Network.RemoteSource
import com.example.mvvp.R
import com.example.mvvp.database.LocalSource
import com.example.mvvp.model.ProductRepo
import kotlinx.coroutines.launch
import com.airbnb.lottie.LottieAnimationView

class AllActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: ListAd
    private lateinit var viewModel: AllProductViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: LottieAnimationView
    private lateinit var errorImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all)
        recyclerView = findViewById(R.id.cartRecyclerView)
        progressBar = findViewById(R.id.lottieAnimationView);
        errorImage =findViewById(R.id.errorImageView)
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

        /*viewModel.products.observe(this, Observer { products ->
            recyclerAdapter.submitList(products)
        })

        viewModel.error.observe(this, Observer { errorMsg ->
            // Show error message to the user
        })*/
        lifecycleScope.launch {
            viewModel.products.collect { state ->
                when (state) {
                    is ProductState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        errorImage.visibility = View.GONE
                    }
                    is ProductState.Success -> {
                        progressBar.visibility = View.GONE
                        errorImage.visibility = View.GONE
                        recyclerAdapter.submitList(state.products)
                    }
                    is ProductState.Error -> {
                        progressBar.visibility = View.GONE
                        errorImage.visibility = View.VISIBLE

                    }
                }
            }
        }

        // Collecting error states
        lifecycleScope.launch {
            viewModel.error.collect { errorMsg ->
                if (errorMsg != null) {
                }
            }
        }
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