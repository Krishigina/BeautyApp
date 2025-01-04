package com.example.beautyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductCardAdapter(
    var productCards: List<ProductCard>
) : RecyclerView.Adapter<ProductCardAdapter.ProductCardViewHolder>() {

    inner class ProductCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProduct: ImageView = itemView.findViewById(R.id.ivProduct)
        val ivLikedProduct: ImageView = itemView.findViewById(R.id.ivLikedProduct)
        val tvProductTitle: TextView = itemView.findViewById(R.id.tvProductTitle)
        val tvProductDescription: TextView = itemView.findViewById(R.id.tvProductDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductCardViewHolder(view)
    }

    override fun getItemCount(): Int = productCards.size

    override fun onBindViewHolder(holder: ProductCardViewHolder, position: Int) {
        val product = productCards[position]

        holder.ivProduct.setImageResource(product.photo)
        holder.tvProductTitle.text = product.name
        holder.tvProductDescription.text = product.description

        holder.ivLikedProduct.setImageResource(
            if (product.isLiked) R.drawable.ic_liked_product else R.drawable.ic_dont_liked_product
        )

        holder.ivLikedProduct.setOnClickListener {
            product.isLiked = !product.isLiked
            notifyItemChanged(position)
        }
    }
}
