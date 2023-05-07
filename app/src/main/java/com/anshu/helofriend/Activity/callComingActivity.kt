package com.anshu.helofriend.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshu.Helofriend.databinding.ActivityCallComingBinding
import com.anshu.helofriend.Adapters.GirlOnlineAdapter
import com.anshu.helofriend.Model.UsersMoney
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.annotations.NotNull


class callComingActivity : AppCompatActivity() {
    lateinit var binding: ActivityCallComingBinding

    private lateinit var girlOnlineAdapter: GirlOnlineAdapter
//
//    var auth: FirebaseAuth? = null
//    var database: FirebaseDatabase? = null
//    var coins: Long = 0
//    var permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
//    private val requestCode = 1
//
//    var users: UsersMoney? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallComingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()



        binding.girlRec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.girlRec.adapter = girlOnlineAdapter
//        val adapter = GirlOnlineAdapter(onlineList.size)


//        val currentUser = auth!!.currentUser
/*
        database!!.reference.child("profiles")
//            .child(currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(@NotNull snapshot: DataSnapshot) {
//                    progress.dismiss()
                    users = snapshot.getValue(UsersMoney::class.java)
                    coins = this@callComingActivity.users!!.coins
                    binding.coins.text = "You have: $coins"
                    Glide.with(this@callComingActivity)
                        .load(users!!.profile)
                        .into(binding.profile)
                }

                override fun onCancelled(@NotNull error: DatabaseError) {}
            })
*/

        /*binding.findButton.setOnClickListener {
            if (isPermissionsGranted()) {
                if (coins > 5) {
                    coins = coins - 5
                    database!!.reference.child("profiles")
//                        .child(currentUser.uid)
                        .child("coins")
                        .setValue(coins)
                    val intent = Intent(this@callComingActivity, GirlcallActivity::class.java)
                    intent.putExtra("profile", users!!.profile)
                    startActivity(intent)
                    //startActivity(new Intent(MainActivity.this, ConnectingActivity.class));
                } else {
                    Toast.makeText(
                        this@callComingActivity,
                        "Insufficient Coins",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                askPermissions()
            }
        }
    }
*/
  /*  private fun askPermissions() {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    private fun isPermissionsGranted(): Boolean {

        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) return false
        }
        return true
    }*/
}}