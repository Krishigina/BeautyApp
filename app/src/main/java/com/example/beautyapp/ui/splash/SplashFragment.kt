package com.example.beautyapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.beautyapp.databinding.FragmentSplashBinding
import com.example.beautyapp.ui.searchpage.SearchpageViewModel

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private lateinit var splashViewModel: SplashViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        splashViewModel =
            ViewModelProvider(this).get(SplashViewModel::class.java)

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val root: View = binding.root

        splashViewModel.checkUserAuthorization(findNavController())

        splashViewModel.navigateTo.observe(viewLifecycleOwner) { destinationId ->
            destinationId?.let {
                findNavController().navigate(it)
                splashViewModel.onNavigationComplete()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}