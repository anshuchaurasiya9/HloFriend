package com.anshu.helofriend.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.anshu.Helofriend.databinding.ActivityCoinRedeemBinding

class CoinRedeemActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoinRedeemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinRedeemBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

    }
}