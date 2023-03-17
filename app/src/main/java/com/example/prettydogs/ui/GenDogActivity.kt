package com.example.prettydogs.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.prettydogs.PrettyDogApplication
import com.example.prettydogs.R
import com.example.prettydogs.databinding.ActivityGenDogBinding
import com.example.prettydogs.model.DogImage
import com.example.prettydogs.networking.Response
import com.example.prettydogs.viewmodel.MainViewModel
import com.example.prettydogs.viewmodel.MainViewModelFactory


class GenDogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGenDogBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gen_dog)

        val repository = (application as PrettyDogApplication).dogImageRepository
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository)
        )[MainViewModel::class.java]

        binding.toolbar.toolbarTitle.text = getString(R.string.generate_dogs)

        binding.toolbar.backBtn.setOnClickListener {
            finish()
        }

        mainViewModel.getDogImage.observe(this) { response ->
            when (response) {
                is Response.Loading -> {}
                is Response.Success -> {
                    response.data?.let {
                        val imageUrl : String? = it.imageUrl
                        binding.progressCircular.visibility = View.INVISIBLE
                        if (!imageUrl.isNullOrEmpty()){
                            Glide.with(this)
                                .load(imageUrl).listener(object : RequestListener<Drawable> {
                                    override fun onLoadFailed(
                                        e: GlideException?,
                                        model: Any?,
                                        target: Target<Drawable>?,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        binding.progressCircular.visibility = View.INVISIBLE
                                        return false
                                    }

                                    override fun onResourceReady(
                                        resource: Drawable?,
                                        model: Any?,
                                        target: Target<Drawable>?,
                                        dataSource: DataSource?,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        binding.progressCircular.visibility = View.INVISIBLE
                                        return false
                                    }
                                })
                                .into(binding.dogIv)
                            mainViewModel.addImage(DogImage(0, imageUrl))
                        } else {
                            Toast.makeText(
                                this,
                                "Unable to load image",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                is Response.Error -> {
                    Toast.makeText(this, response.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.generateTv.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE
            mainViewModel.getDogImage()
        }
    }

}