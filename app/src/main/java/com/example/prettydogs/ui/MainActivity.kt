package com.example.prettydogs.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.prettydogs.PrettyDogApplication
import com.example.prettydogs.R
import com.example.prettydogs.databinding.ActivityMainBinding
import com.example.prettydogs.viewmodel.MainViewModel
import com.example.prettydogs.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.genDogBtn.setOnClickListener {
            startActivity(Intent(this, GenDogActivity::class.java))
        }

        binding.showDogBtn.setOnClickListener {
            startActivity(Intent(this, RecentDogActivity::class.java))
        }
    }
}