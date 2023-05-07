package com.anshu.helofriend.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anshu.Helofriend.databinding.ActivityGirlcallBinding
import io.agora.rtc2.RtcEngine

class GirlcallActivity : AppCompatActivity() {
    lateinit var binding: ActivityGirlcallBinding

    // Fill the temp token generated on Agora Console.
    private var token =
        "007eJxTYFC796n4qHhPXXjj6gVLuPdLr+50cbi/U1YluZOXMZCd+bECg1lKqnGaQbJpsoW5sUmKsVGSUaKFhXFKUmKKuUWKiaWhYqNZSkMgI0PgMVsWRgYIBPE5GTxy8t2KMlPzUhgYAFuyHrs="

    private val appId = "6de3f0c5c8734d32b2a883dbad78d491"

    private val channelName = "HloFriend"

    private var agoraEngine: RtcEngine? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGirlcallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoice.setOnClickListener {
            val intent = Intent(this, GirlVoiceActivity::class.java)
            startActivity(intent)
        }


        binding.btnVideo.setOnClickListener {
            val intent = Intent(this, GirlVideoActivity::class.java)
            startActivity(intent)
        }
    }


}