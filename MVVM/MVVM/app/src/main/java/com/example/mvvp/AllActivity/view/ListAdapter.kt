package com.example.mvvp.AllActivity.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.downloadworker.model.Product
import com.example.mvvp.R
import java.lang.String
import kotlin.Boolean
import kotlin.Int
import kotlin.Unit


class myDiffUtil : DiffUtil.ItemCallback<Product>()
{
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}
class ListAd(private var myListener: (Product) -> Unit): ListAdapter<Product, ListAd.ViewHolder>(myDiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.product_frame , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)

        holder.titleView.setText(current.title)
        holder.descriptionView.setText(current.description)
        holder.priceView.setText(String.valueOf(current.price))
        holder.brandView.setText(current.brand)
        holder.ratingBar.rating = String.valueOf(current.rating).toFloat()
        Glide.with(holder.img.context)
            .load(current.thumbnail)
            .into(holder.img)
        holder.rmProduct.setOnClickListener{
            myListener.invoke(current)
        }

    }

    class ViewHolder(private val item: View): RecyclerView.ViewHolder(item){
        val img: ImageView = item.findViewById(R.id.imageView)

        val titleView: TextView = item.findViewById(R.id.titleView)
        val descriptionView : TextView = item.findViewById(R.id.descriptionView)
        val priceView: TextView = item.findViewById(R.id.priceView)
        val brandView: TextView = item.findViewById(R.id.brandView)
        val ratingBar: RatingBar = item.findViewById(R.id.ratingBar)
        val rmProduct: Button = item.findViewById(R.id.rmProduct)

        val cons: ConstraintLayout = item.findViewById(R.id.clConst)
    }
}