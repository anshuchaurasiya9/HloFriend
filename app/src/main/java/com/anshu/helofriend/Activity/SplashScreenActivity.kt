package com.anshu.helofriend.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.anshu.Helofriend.R


@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Onboarding_Activity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}