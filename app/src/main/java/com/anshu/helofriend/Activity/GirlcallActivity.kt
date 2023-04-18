package com.anshu.helofriend.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anshu.Helofriend.databinding.ActivityGirlcallBinding

class GirlcallActivity : AppCompatActivity() {
    lateinit var binding: ActivityGirlcallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGirlcallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoice.setOnClickListener {
            val intent = Intent (this, GirlVoiceActivity::class.java)
            startActivity(intent)
        }


        binding.btnVideo.setOnClickListener {
            val intent = Intent (this, GirlVideoActivity::class.java)
            startActivity(intent)
        }
    }
}