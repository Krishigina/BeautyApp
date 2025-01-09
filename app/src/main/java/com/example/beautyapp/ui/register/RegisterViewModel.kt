package com.example.beautyapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyapp.utils.SharedPrefsManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> get() = _registrationResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            _errorMessage.value = "Не все обязательные поля заполнены"
            return
        }

        if (password != confirmPassword) {
            _errorMessage.value = "Пароли не совпадают"
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    currentUser?.let {
                        val userInfo = hashMapOf(
                            "uid" to it.uid,
                            "email" to email,
                            "username" to name
                        )
                        firestore.collection("Users")
                            .document(it.uid)
                            .set(userInfo)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    _registrationResult.value = true
                                    SharedPrefsManager.setUserAuthorized(true)
                                } else {
                                    _errorMessage.value = "Ошибка сохранения данных: ${firestoreTask.exception?.message}"
                                    _registrationResult.value = false
                                }
                            }
                    }
                } else {
                    _errorMessage.value = "Ошибка регистрации: ${task.exception?.message}"
                    _registrationResult.value = false
                }
            }
    }
}
