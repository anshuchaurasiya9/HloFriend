package com.anshu.helofriend.Activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.anshu.Helofriend.R
import com.anshu.Helofriend.databinding.ActivityMainBinding
import com.anshu.helofriend.fragment.ProfileFragment
import com.anshu.helofriend.fragment.SessionFragment
import com.anshu.helofriend.fragment.TopicFragment
import com.anshu.helofriend.fragment.HomeFragment


class dashboard : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var homeFragment: Fragment
    private lateinit var sessionFragment: Fragment
    private lateinit var profileFragment: Fragment
    private lateinit var topicFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val sessionFragment = SessionFragment()
        val topicFragment = TopicFragment()

        supportFragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit()



        binding.flotingbtn.setOnClickListener {
            val intent = Intent (this, callComingActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, homeFragment)
                        .commit()
                    true
                }
                R.id.session -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, sessionFragment).commit()
                    true
                }
                R.id.topic -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, topicFragment)
                        .commit()
                    true
                }

                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, profileFragment).commit()
                    true
                }
                else -> false
            }

        }

    }

}