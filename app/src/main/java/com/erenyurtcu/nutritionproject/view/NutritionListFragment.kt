package com.erenyurtcu.nutritionproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erenyurtcu.nutritionproject.adapter.NutritionRecyclerAdapter
import com.erenyurtcu.nutritionproject.databinding.FragmentNutritionListBinding
import com.erenyurtcu.nutritionproject.service.NutritionAPI
import com.erenyurtcu.nutritionproject.viewmodel.NutritionListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class NutritionListFragment : Fragment() {

    private var _binding : FragmentNutritionListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NutritionListViewModel
    private val nutritionRecyclerAdapter = NutritionRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNutritionListBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NutritionListViewModel::class.java]
        viewModel.refreshData()

        binding.nutritionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.nutritionRecyclerView.adapter = nutritionRecyclerAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.nutritionRecyclerView.visibility = View.GONE
            binding.nutritionErrorMessage.visibility = View.GONE
            binding.nutritionProgressBar.visibility = View.VISIBLE
            viewModel.refreshDataFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.nutritions.observe(viewLifecycleOwner){
            binding.nutritionRecyclerView.visibility = View.VISIBLE
            nutritionRecyclerAdapter.updateNutritionList(it)
        }
        viewModel.nutritionErrorMessage.observe(viewLifecycleOwner){
            if(it){
                binding.nutritionErrorMessage.visibility = View.VISIBLE
                binding.nutritionRecyclerView.visibility = View.GONE
            } else{
                binding.nutritionErrorMessage.visibility = View.GONE
            }
        }
        viewModel.nutritionIsLoading.observe(viewLifecycleOwner){
            if(it){
                binding.nutritionErrorMessage.visibility = View.GONE
                binding.nutritionRecyclerView.visibility = View.GONE
                binding.nutritionProgressBar.visibility = View.VISIBLE
            } else{
                binding.nutritionProgressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}