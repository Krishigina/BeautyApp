package com.example.beautyapp.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyapp.R
import com.example.beautyapp.utils.SharedPrefsManager

class SplashViewModel : ViewModel() {

    private val _navigateTo = MutableLiveData<Int?>()
    val navigateTo: LiveData<Int?> get() = _navigateTo

    fun checkUserAuthorization() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (SharedPrefsManager.isUserAuthorized()) {
                // Переход к MainFragment, если пользователь авторизован
                _navigateTo.value = R.id.action_splashFragment_to_mainFragment
            } else {
                // Переход к WelcomeFragment, если пользователь не авторизован
                _navigateTo.value = R.id.action_splashFragment_to_navigation_welcome
            }
        }, 2000) // Задержка 2 секунды
    }

    fun onNavigationComplete() {
        _navigateTo.value = null
    }
}