package com.example.beautyapp

data class ProductCard(
    val photo: Int,
    val name: String,
    val description: String,
    var isLiked: Boolean
)