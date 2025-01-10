package com.example.beautyapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyapp.model.ProductCard
import com.google.firebase.firestore.FirebaseFirestore

class HomepageViewModel : ViewModel() {

    private val _products = MutableLiveData<MutableList<ProductCard>>()

    val products: LiveData<MutableList<ProductCard>> = _products

    fun loadItems() {
        val db = FirebaseFirestore.getInstance()
        db.collection("ProductCards")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val lists = mutableListOf<ProductCard>()
                for (document in querySnapshot.documents) {
                    val product = document.toObject(ProductCard::class.java)
                    if (product != null) {
                        lists.add(product)
                    }
                }
                _products.value = lists
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Ошибка при загрузке данных: ${exception.message}", exception)
            }
    }
}