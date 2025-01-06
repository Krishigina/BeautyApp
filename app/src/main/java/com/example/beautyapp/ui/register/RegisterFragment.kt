package com.example.beautyapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.beautyapp.R
import com.example.beautyapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(RegisterViewModel::class.java)

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRegister.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRegister.setOnClickListener {
            if (binding.etName.text.toString().isEmpty() ||
                binding.etEmail.text.toString().isEmpty() ||
                binding.etPassword.text.toString().isEmpty() ||
                binding.etControlPassword.text.toString().isEmpty()) {

                Toast.makeText(requireContext(), "Не все обязательные поля заполнены", Toast.LENGTH_LONG).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        currentUser?.let {
                            val userInfo = hashMapOf(
                                "email" to binding.etEmail.text.toString(),
                                "username" to binding.etName.text.toString(),
                                "password" to binding.etPassword.text.toString()
                            )

                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(it.uid)
                                .setValue(userInfo)
                                .addOnCompleteListener { databaseTask ->
                                    if (databaseTask.isSuccessful) {
                                        Toast.makeText(requireContext(), "Регистрация успешна", Toast.LENGTH_LONG).show()
                                        findNavController().navigate(R.id.action_navigation_register_to_mainFragment)
                                    } else {
                                        Toast.makeText(requireContext(), "Ошибка сохранения данных: ${databaseTask.exception?.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Ошибка регистрации: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}