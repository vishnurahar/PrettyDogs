package com.example.prettydogs.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prettydogs.PrettyDogApplication
import com.example.prettydogs.R
import com.example.prettydogs.adapter.DogImageListAdapter
import com.example.prettydogs.databinding.ActivityRecentDogBinding
import com.example.prettydogs.networking.Response
import com.example.prettydogs.viewmodel.RecentViewModel
import com.example.prettydogs.viewmodel.RecentViewModelFactory

class RecentDogActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecentDogBinding
    private lateinit var recentViewModel: RecentViewModel
    private val dogImageListAdapter by lazy { DogImageListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recent_dog)

        val repository = (application as PrettyDogApplication).dogImageRepository
        recentViewModel = ViewModelProvider(
            this,
            RecentViewModelFactory(repository)
        )[RecentViewModel::class.java]

        binding.toolbar.toolbarTitle.text = getString(R.string.my_recently_generated_dogs)

        binding.toolbar.backBtn.setOnClickListener {
            finish()
        }

        binding.dogsRv.apply {
            val horizontalLayout = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            layoutManager = horizontalLayout
            adapter = dogImageListAdapter
        }

        recentViewModel.getDogImage()

        recentViewModel.getAllDogImageResult.observe(this) { response ->
            when(response){
                is Response.Loading -> {}
                is Response.Success -> {
                    dogImageListAdapter.submitList(response.data)
                }
                is Response.Error -> {
                    Toast.makeText(this, response.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.clearDogBtn.setOnClickListener {
            recentViewModel.clearAllImages()
            recentViewModel.getDogImage()
        }

    }
}