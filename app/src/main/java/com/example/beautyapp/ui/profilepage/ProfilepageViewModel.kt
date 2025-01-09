package com.example.beautyapp.ui.profilepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfilepageViewModel : ViewModel() {

    private val _profileName: MutableLiveData<String> = MutableLiveData("")
    val profileName: LiveData<String> = _profileName
    private val _email: MutableLiveData<String> = MutableLiveData("")
    val email: LiveData<String> = _email

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        _email.value = auth.currentUser?.email

        auth.currentUser?.uid?.let { uid ->
            firestore.collection("Users")
                .document(uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.exists()) {
                            val username = document.getString("username")
                            _profileName.value = username ?: "Имя не установлено"
                        } else {
                            _profileName.value = "Пользователь не найден"
                        }
                    } else {
                        _profileName.value = "Ошибка при загрузке данных"
                        Log.e("Firebase", "Ошибка при получении данных", task.exception)
                    }
                }
        }
    }
}
