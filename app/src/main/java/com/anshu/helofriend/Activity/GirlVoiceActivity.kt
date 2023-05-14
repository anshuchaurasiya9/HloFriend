package com.anshu.helofriend.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.anshu.Helofriend.R
import com.anshu.Helofriend.databinding.ActivityGirlVoiceBinding
import com.anshu.helofriend.Model.Wallet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import io.agora.rtc2.*


class GirlVoiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityGirlVoiceBinding
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var chronometer: Chronometer
    private val callDurationInMillis: Long = 600000 // 10 minutes in milliseconds


    private val PERMISSION_REQ_ID = 22
    private val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )


    // Fill the App ID of your project generated on Agora Console.
    private val appId = "6de3f0c5c8734d32b2a883dbad78d491"

    // Fill the channel name.
    private val channelName = "HloFriend"

    // Fill the temp token generated on Agora Console.
    private val token =
        "007eJxTYNC5fDd+yiqji6cOZIXYTWjJqzJdxvB7v7Gk1Pm1p9XjjfYrMJilpBqnGSSbJluYG5ukGBslGSVaWBinJCWmmFukmFgaLp9lltIQyMiwRmUHEyMDBIL4nAweOfluRZmpeSkMDAASLSF/"

    // An integer that identifies the local user.
    private val uid = 0

    // Track the status of your connection
    private var isJoined = false

    // Agora engine instance
    private var agoraEngine: RtcEngine? = null

    // UI elements
    private var infoText: TextView? = null
    private var joinLeaveButton: Button? = null

    private fun checkSelfPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            REQUESTED_PERMISSIONS[0]
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun showMessage(message: String?) {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupVoiceSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
        } catch (e: java.lang.Exception) {
            throw RuntimeException("Check the error.")
        }
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote user joining the channel.
        override fun onUserJoined(uid: Int, elapsed: Int) {
            runOnUiThread { infoText!!.text = "Remote user joined: $uid" }
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            // Successfully joined a channel
            isJoined = true
            showMessage("Joined Channel $channel")
            runOnUiThread { infoText!!.text = "Waiting for a remote user to join" }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            // Listen for remote users leaving the channel
            showMessage("Remote user offline $uid $reason")
            if (isJoined) runOnUiThread { infoText!!.text = "Waiting for a remote user to join" }
        }

        override fun onLeaveChannel(stats: RtcStats?) {
            // Listen for the local user leaving the channel
            runOnUiThread { infoText!!.text = "Press the button to join a channel" }
            isJoined = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGirlVoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        /*val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userCoinsRef = FirebaseDatabase.getInstance().reference.child("User").child(userId!!).child("coins")

        userCoinsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentCoins = dataSnapshot.getValue(Wallet::class.java) ?: 0
                Log.d("current", Gson().toJson(currentCoins))
//                    val updatedCoins = currentCoins - 10
                    // Start the countdown timer
                    startCountdownTimer()
                    // Update the user's coin count in the database
//                    userCoinsRef.setValue(updatedCoins)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }
        })*/


        countDownTimer = object : CountDownTimer(callDurationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                chronometer.stop()
                // Perform actions when the call duration is finished
                // e.g., end the call, display a notification, etc.

            }
        }

        binding.flBtnLeave.setOnClickListener {
            agoraEngine!!.leaveChannel()
            finish()
        }

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID)
        }
        setupVoiceSDKEngine()

        // Set up access to the UI elements
        joinLeaveButton = findViewById(R.id.joinLeaveButton)
        infoText = findViewById(R.id.infoText)


    }

      override fun onDestroy() {
        super.onDestroy()
        agoraEngine!!.leaveChannel()
          countDownTimer.cancel()
          chronometer.stop()
        Thread {
            RtcEngine.destroy()
            agoraEngine = null
        }.start()

}

        private fun joinChannel() {
            val options = ChannelMediaOptions()
            options.autoSubscribeAudio = true
            // Set both clients as the BROADCASTER.
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            // Set the channel profile as BROADCASTING.
            options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
            startCountdownTimer()
            // Join the channel with a temp token.
            // You need to specify the user ID yourself, and ensure that it is unique in the channel.
            agoraEngine!!.joinChannel(token, channelName, uid, options)
        }

    private fun startCountdownTimer() {
        chronometer = findViewById(R.id.chronometer)
        chronometer.format = "Call Duration: %s"
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
    }

    fun joinLeaveChannel(view: View) {
            if (isJoined) {
                agoraEngine!!.leaveChannel()
                joinLeaveButton!!.text = "Join"
                countDownTimer.cancel()
                chronometer.stop()
            } else {
                joinChannel()
                joinLeaveButton!!.text = "Leave"
            }

        }

    fun onMuteButtonClick(view: View) {}
    fun onSpeakerButtonClick(view: View) {}
    fun onCameraOffButtonClick(view: View) {}
}

