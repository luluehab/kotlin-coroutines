package com.example.mvvp.cartActivity.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.downloadworker.model.Product
import com.example.mvvp.R

class MyDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}

class ListAd(private var myListener: (Product) -> Unit) : ListAdapter<Product, ListAd.FavViewHolder>(MyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.cart_product, parent, false)
        return FavViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val current = getItem(position)

        holder.titleView.text = current.title
        holder.priceView.text = current.price.toString()  // Correct usage
        holder.brandView.text = current.brand

        // Handle potential null thumbnail safely
        current.thumbnail?.let {
            Glide.with(holder.img.context)
                .load(it)
                .into(holder.img)
        }

        holder.rmProduct.setOnClickListener {
            myListener.invoke(current)
        }
    }

    class FavViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val img: ImageView = item.findViewById(R.id.imageView)
        val titleView: TextView = item.findViewById(R.id.titleView)
        val priceView: TextView = item.findViewById(R.id.priceView)
        val brandView: TextView = item.findViewById(R.id.brandView)
        val rmProduct: Button = item.findViewById(R.id.rmProduct)
    }
}
