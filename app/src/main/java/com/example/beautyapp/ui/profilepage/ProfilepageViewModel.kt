package com.example.beautyapp.ui.profilepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class ProfilepageViewModel : ViewModel() {

    private val _profileName: MutableLiveData<String> = MutableLiveData("")
    val profileName: LiveData<String> = _profileName
    private val _email: MutableLiveData<String> = MutableLiveData("")
    val email: LiveData<String> = _email

    init {
        _email.value = Firebase.auth.currentUser?.email

        Firebase.auth.uid?.let { uid ->
            Firebase.database.getReference("Users").child(uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val snapshot = task.result

                    if (snapshot != null && snapshot.exists()) {
                        val username = snapshot.child("username").getValue(String::class.java)

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