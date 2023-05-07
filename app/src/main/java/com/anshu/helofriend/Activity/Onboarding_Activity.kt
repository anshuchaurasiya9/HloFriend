package com.anshu.helofriend.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.anshu.Helofriend.R
import com.anshu.helofriend.Adapters.ViewPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class Onboarding_Activity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    private lateinit var auth : FirebaseAuth
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        auth = FirebaseAuth.getInstance()

        val dotsIndicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        val btn_continue = findViewById<Button>(R.id.btn_continue)


        viewPager = findViewById(R.id.viewPager)
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        dotsIndicator.attachTo(viewPager)

        btn_continue.setOnClickListener {
            val intent = Intent(this, PhoneAuthActivity::class.java)
            startActivity(intent)
        }


        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            //TO SET BACKGROUND COLOR OF EACH SLIDE
            override fun onPageSelected(position: Int) {
                if (position == 1) {
                    //relativeLayout.setBackgroundResource(R.drawable.bg1)
                }
                if (position == 4) {
                    btn_continue.visibility = View.VISIBLE

                }
            }
            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            startActivity(Intent(this , dashboard::class.java))
        }
    }
}