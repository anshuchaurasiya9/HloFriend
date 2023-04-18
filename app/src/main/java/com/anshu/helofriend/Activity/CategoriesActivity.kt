package com.anshu.helofriend.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anshu.Helofriend.databinding.ActivityCategoriesBinding

class CategoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCategory.setOnClickListener {
            val intent = Intent(this, GirlcallActivity::class.java)
            startActivity(intent)
        }
    }
}