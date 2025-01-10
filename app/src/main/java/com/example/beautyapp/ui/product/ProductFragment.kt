package com.example.beautyapp.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyapp.databinding.FragmentProductBinding
import com.example.beautyapp.ui.splash.SplashViewModel

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private lateinit var splashViewModel: SplashViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        splashViewModel =
            ViewModelProvider(this).get(SplashViewModel::class.java)

        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}