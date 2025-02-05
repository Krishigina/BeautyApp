package com.example.beautyapp.ui.homepage

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyapp.model.ProductCard
import com.example.beautyapp.adapter.ProductCardAdapter
import com.example.beautyapp.R
import com.example.beautyapp.databinding.FragmentHomepageBinding
import com.example.beautyapp.ui.login.HomepageViewModel


class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homepageViewModel: HomepageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomepageViewModel::class.java)

        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProducts()

        //        val productCards = listOf(
//            ProductCard(
//                R.drawable.product_item,
//                "Granactive Retinoid 5%",
//                "Эмульсия с 5% улучшенной формы ретинола The Ordinary 5% etc",
//                false
//            ),
//            ProductCard(
//                R.drawable.product_item,
//                "Granactive Retinoid 5%",
//                "Эмульсия с 5% улучшенной формы ретинола The Ordinary 5% etc",
//                false
//            ),
//            ProductCard(
//                R.drawable.product_item,
//                "Granactive Retinoid 5%",
//                "Эмульсия с 5% улучшенной формы ретинола The Ordinary 5% etc",
//                false
//            ),
//            ProductCard(
//                R.drawable.product_item,
//                "Granactive Retinoid 5%",
//                "Эмульсия с 5% улучшенной формы ретинола The Ordinary 5% etc",
//                false
//            )
//        )
//
//        val productAdapter = ProductCardAdapter(productCards)
//        binding.rvCosmeticProduct.apply {
//            layoutManager = LinearLayoutManager(requireContext(),  LinearLayoutManager.HORIZONTAL, false)
//            adapter = productAdapter
//        }
//

    }

    private fun initProducts() {
        binding.pbProductsCard.visibility = View.VISIBLE
        homepageViewModel.products.observe(viewLifecycleOwner, Observer {
            binding.rvCosmeticProduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvCosmeticProduct.adapter = ProductCardAdapter(it)
            binding.pbProductsCard.visibility = View.GONE
        })
        homepageViewModel.loadItems()

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing) // 25dp
        binding.rvCosmeticProduct.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.right = spacingInPixels
            }
        })
    }
}