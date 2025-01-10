package com.example.beautyapp.ui.product

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.beautyapp.R
import com.example.beautyapp.databinding.FragmentProductBinding
import com.example.beautyapp.model.ProductCard
import com.example.beautyapp.ui.splash.SplashViewModel

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private lateinit var splashViewModel: SplashViewModel

    private val binding get() = _binding!!

    private lateinit var product: ProductCard


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получение данных из аргументов фрагмента
        arguments?.let {
             product = it.getParcelable("object")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        splashViewModel =
            ViewModelProvider(this).get(SplashViewModel::class.java)

        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        product.image?.let {
            if (it.isNotEmpty()) {
                setImageFromBase64(it, binding.ivProductPhoto)
            } else {
                binding.ivProductPhoto.setImageResource(R.drawable.product_item) // Замените на ваш ресурс заглушки
            }
        }

        binding.tvProductname.text = product.product_name
        binding.tvProductdescription.text = product.description

        // Добавьте другие привязки для элементов UI, если требуется

        binding.ivArrowLeftProfile.setOnClickListener { findNavController().popBackStack() }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setImageFromBase64(base64String: String, imageView: android.widget.ImageView) {
        try {
            // Декодируем строку Base64 в байты
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)

            // Преобразуем байты в Bitmap
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

            // Устанавливаем Bitmap в ImageView
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            // Обработать ошибку в случае неверного формата Base64
        }
    }
}