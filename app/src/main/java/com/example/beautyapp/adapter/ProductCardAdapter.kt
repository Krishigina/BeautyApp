package com.example.beautyapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.beautyapp.model.ProductCard
import com.example.beautyapp.R
import com.example.beautyapp.databinding.ItemProductBinding
import com.example.beautyapp.ui.product.ProductFragment
import kotlin.io.encoding.ExperimentalEncodingApi

class ProductCardAdapter(
    val productCards: List<ProductCard>
) : RecyclerView.Adapter<ProductCardAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCardAdapter.ViewHolder {
        context = parent.context
        val binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = productCards.size


    fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }


    @OptIn(ExperimentalEncodingApi::class)
    override fun onBindViewHolder(holder: ProductCardAdapter.ViewHolder, position: Int) {
        holder.binding.tvProductTitle.text = productCards[position].product_name
        holder.binding.tvProductDescription.text = productCards[position].description

        val requestOptions = RequestOptions().transform()

        val base64Image = productCards[position].image
        val bitmap = decodeBase64ToBitmap(base64Image)

        if (bitmap != null) {
            Glide.with(holder.itemView.context)
                .load(bitmap)
                .apply(requestOptions)
                .into(holder.binding.ivProduct)

        } else {
            holder.binding.ivProduct.setImageResource(R.drawable.product_item)
        }

        holder.itemView.setOnClickListener {
            val fragment = ProductFragment()

            val bundle = Bundle().apply {
                putParcelable("object", productCards[position])
            }

            fragment.arguments = bundle

            val navController = (context as AppCompatActivity).findNavController(R.id.mainContainerFragment)
            navController.navigate(R.id.action_homepageFragment_to_productFragment, bundle)
        }

    }
}
