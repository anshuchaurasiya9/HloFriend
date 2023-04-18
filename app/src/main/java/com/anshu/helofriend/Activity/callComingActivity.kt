package com.anshu.helofriend.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anshu.Helofriend.R
import com.anshu.Helofriend.databinding.ActivityCallComingBinding

class callComingActivity : AppCompatActivity() {
    lateinit var binding: ActivityCallComingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallComingBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}