package com.anshu.helofriend.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anshu.Helofriend.databinding.ActivityCategoriesBinding
import com.google.android.material.chip.Chip

class CategoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)



        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                // At least one chip is checked
                val checkedChip = findViewById<Chip>(checkedId)
                checkedChip.isChecked = true
                binding.btnCategory.visibility = View.VISIBLE
                binding.btnCategory.setOnClickListener {
                    val intent = Intent(this, GirlcallActivity::class.java)
                    startActivity(intent)
                }

            } else {
                // No chip is checked
                // Show error message or take other action
                binding.btnCategory.visibility = View.GONE

                Toast.makeText(this, "Please select a Category", Toast.LENGTH_SHORT).show()
            }
        }
    }
}