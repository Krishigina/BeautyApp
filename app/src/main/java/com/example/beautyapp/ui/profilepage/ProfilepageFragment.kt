package com.example.beautyapp.ui.profilepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.beautyapp.R
import com.example.beautyapp.databinding.FragmentProfilepageBinding
import com.example.beautyapp.utils.SharedPrefsManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ProfilepageFragment: Fragment() {

    private var _binding: FragmentProfilepageBinding? = null
    private val viewModel: ProfilepageViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfilepageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profileName.observe(viewLifecycleOwner) {
            binding.tvProfileName.text = it
        }

        binding.cvProfileLogout.setOnClickListener{
            SharedPrefsManager.clear()
            Firebase.auth.signOut()
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
            navController.navigate(R.id.navigation_welcome)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}