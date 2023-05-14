package com.anshu.helofriend.Activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.anshu.Helofriend.R
import com.anshu.Helofriend.databinding.ActivityGirlVideoBinding
import com.anshu.helofriend.Model.Wallet
import com.anshu.helofriend.media.RtcTokenBuilder2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import io.agora.rtc2.*
import io.agora.rtc2.video.VideoCanvas


class GirlVideoActivity : AppCompatActivity() {

    lateinit var binding: ActivityGirlVideoBinding
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var chronometer: Chronometer
    private lateinit var handler: Handler
    private lateinit var taskRunnable: Runnable
    private val callDurationInMillis: Long = 600000 // 10 minutes in milliseconds
var remainingCoins = 0
var currentCoins = ""
    var appCertificate = "e2d9d34dff754d0e8a1cdf828a6d36c4"
    var expirationTimeInSeconds = 3600
    // Fill the App ID of your project generated on Agora Console.
    private val appId = "6de3f0c5c8734d32b2a883dbad78d491"

    // Fill the channel name
    private val channelName = "HloFriend"

    // Fill the temp token generated on Agora Console.
    private var token =
        "007eJxTYFC796n4qHhPXXjj6gVLuPdLr+50cbi/U1YluZOXMZCd+bECg1lKqnGaQbJpsoW5sUmKsVGSUaKFhXFKUmKKuUWKiaWhYqNZSkMgI0PgMVsWRgYIBPE5GTxy8t2KMlPzUhgYAFuyHrs="

    // An integer that identifies the local user.
    private val uid = 0
    private var isJoined = false

    private var agoraEngine: RtcEngine? = null

    //SurfaceView to render local video in a Container.
    private var localSurfaceView: SurfaceView? = null

    //SurfaceView to render Remote video in a Container.
    private var remoteSurfaceView: SurfaceView? = null

    val PERMISSION_REQ_ID = 22
    val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )

    fun checkSelfPermission(): Boolean {
        return !(ContextCompat.checkSelfPermission(
            this,
            REQUESTED_PERMISSIONS[0]
        ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    REQUESTED_PERMISSIONS[1]
                ) != PackageManager.PERMISSION_GRANTED)
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

    private fun setupVideoSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
            // By default, the video module is disabled, call enableVideo to enable it.
            agoraEngine!!.enableVideo()
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGirlVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)


//        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
// Specify the path to the data you want to filter
        val dataReference: DatabaseReference = databaseReference.child("User")
        var nodeName = this.intent.getStringExtra("fullName")
        Log.d("NodeName of payment", nodeName.toString())
// Apply a query to filter the data
        val query: Query = dataReference.orderByChild(nodeName.toString())

// Add a ValueEventListener to read the data
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called when the data is changed or initially loaded

                // Retrieve the data from the dataSnapshot
                val value = dataSnapshot.children

//                var userArr = dataSnapshot.toString().split("{")
//                var coinsArr = userArr[3].split("=")
//                Log.d("Userreader", coinsArr[3].split(",")[0])
//                currentCoins = coinsArr[3].split(",")[0]
//                remainingCoins = currentCoins.toInt()
//                binding.addCoins.text = coinsArr[3].split(",")[0]
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Faliure", databaseError.toString())
                // This method is called if the data retrieval is canceled or fails
                // Handle the error as needed
            }
        })

  /*      countDownTimer = object : CountDownTimer(callDurationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Not used in this example
            }
            override fun onFinish() {
                chronometer.stop()
                // Perform actions when the call duration is finished
                // e.g., end the call, display a notification, etc.
            }
        }*/


        val tokenBuilder = RtcTokenBuilder2()
        val timestamp = (System.currentTimeMillis() / 1000 + expirationTimeInSeconds).toInt()

        println("UID token")
        val result = tokenBuilder.buildTokenWithUid(
            appId, appCertificate,
            channelName, uid, RtcTokenBuilder2.Role.ROLE_PUBLISHER, timestamp, timestamp
        )
        println(result)
        token = result

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID)
        }
        setupVideoSDKEngine()
    }

    override fun onDestroy() {
        super.onDestroy()
        agoraEngine!!.stopPreview()
        agoraEngine!!.leaveChannel()
//        countDownTimer.cancel()
        chronometer.stop()

        Thread {
            RtcEngine.destroy()
            agoraEngine = null
        }.start()

    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            showMessage("Remote user joined $uid")
            // Set the remote video view
            runOnUiThread { setupRemoteVideo(uid) }
        }
        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            isJoined = true
            showMessage("Joined Channel $channel")
        }
        override fun onUserOffline(uid: Int, reason: Int) {
            showMessage("Remote user offline $uid $reason")
            runOnUiThread { remoteSurfaceView!!.visibility = View.GONE }
        }
    }

    private fun setupRemoteVideo(uid: Int) {
        remoteSurfaceView = SurfaceView(baseContext)
        remoteSurfaceView!!.setZOrderMediaOverlay(true)
        binding.remoteVideoViewContainer.addView(remoteSurfaceView)
        agoraEngine!!.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
        remoteSurfaceView!!.visibility = View.VISIBLE
    }

    private fun setupLocalVideo() {
        localSurfaceView = SurfaceView(baseContext)
        binding.localVideoViewContainer.addView(localSurfaceView)
        agoraEngine!!.setupLocalVideo(
            VideoCanvas(
                localSurfaceView,
                VideoCanvas.RENDER_MODE_HIDDEN,
                0
            )
        )
    }

    fun joinChannel(view: View) {
        if (checkSelfPermission()) {
            val options = ChannelMediaOptions()

            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            setupLocalVideo()
//            remainingCoins -= 60
            localSurfaceView!!.visibility = View.VISIBLE
            agoraEngine!!.startPreview()
            agoraEngine!!.joinChannel(token, channelName, uid, options)

            startCountdownTimer()
        } else {
            Toast.makeText(applicationContext, "Permissions was not granted", Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun startCountdownTimer() {
        Log.d("method started", "logg")
        chronometer = findViewById(R.id.chronometer)
        chronometer.format = "Call : %s"
        chronometer.base = SystemClock.elapsedRealtime()
//        chronometer.start()
        Log.d("timer", SystemClock.elapsedRealtime().toString())
//        currentCoins = (currentCoins.toInt() - 60).toString()

        handler = Handler()

        taskRunnable = object : Runnable {
            override fun run() {
                // Perform your task here
                // This code will execute every one minute

                handler.postDelayed(this, 60000) // Schedule the next execution after one minute
            }
        }
        chronometer.setOnChronometerTickListener { chronometer ->
            val elapsedMillis = System.currentTimeMillis() - chronometer.base
            val elapsedSeconds = elapsedMillis / 1000
            val elapsedMinutes = elapsedSeconds / 60


            if (elapsedMinutes >= 1) {
                // One minute has completed
                handler.post(taskRunnable) // Start the task
                remainingCoins -= 60
                Log.d("coin deducted", remainingCoins.toString())

            }
        }

        if(remainingCoins < 60){
            showRechargeDialog()
        }

        chronometer.start()
        handler.post(taskRunnable)
        remainingCoins -= 60

        // Start the task initially

    }

    private fun showRechargeDialog() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Coin Recharge")
                .setMessage("Your coins have ended. Recharge now?")
                .setPositiveButton("Recharge") { dialog: DialogInterface?, which: Int ->
                    // Recharge button clicked
                    performRecharge()
                    // Perform recharge logic here
                }
                .setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
                    // Cancel button clicked
                    // Handle cancel logic here
                    cancelRecharge()
                }
                .setCancelable(false) // Prevent dialog dismissal on outside touch or back press

            val dialog = builder.create()
            dialog.show()
        }
    private fun cancelRecharge() {
        startActivity(Intent(this, dashboard::class.java))
    }
    private fun performRecharge() {
        startActivity(Intent(this, WalletActivity::class.java))
    }

    fun leaveChannel(view: View) {
        if (!isJoined) {
            showMessage("Join a channel first")
        } else {
            agoraEngine!!.leaveChannel()
            showMessage("You left the channel")
//            cancelCountdownTimer()
//            countDownTimer.cancel()
            chronometer.stop()
            if (remoteSurfaceView != null) remoteSurfaceView!!.visibility = View.GONE
            if (localSurfaceView != null) localSurfaceView!!.visibility = View.GONE
            isJoined = false
        }
    }

   /* private fun cancelCountdownTimer() {
        countDownTimer.cancel()

    }*/
/*
    private var isMuted = false
//    private var isSpeakerOn = false
    private var isCameraOff = false*/
    fun onMuteButtonClick(view: View) {
        val muteButton = view as FloatingActionButton
        if (muteButton.isSelected) {
            muteButton.isSelected = false
            agoraEngine!!.muteLocalAudioStream(false)
            muteButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_mic))

        } else {
            muteButton.isSelected = true
            agoraEngine!!.muteLocalAudioStream(true)
            muteButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_mic_off))

        }
    }

    fun onSpeakerButtonClick(view: View) {
        val speakerButton = view as FloatingActionButton
        if (speakerButton.isSelected) {
            speakerButton.isSelected = false
            agoraEngine!!.setEnableSpeakerphone(false)
            speakerButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_speaker))


        } else {
            speakerButton.isSelected = true
            speakerButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_speaker_off))

        }

    }

    fun onCameraOffButtonClick(view: View) {
        val cameraButton = view as FloatingActionButton
        if (cameraButton.isSelected) {
            cameraButton.isSelected = false
            agoraEngine!!.muteLocalVideoStream(false)
            localSurfaceView!!.visibility = View.VISIBLE
            cameraButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_camera))

        } else {
            cameraButton.isSelected = true
            agoraEngine!!.muteLocalVideoStream(true)
            localSurfaceView!!.visibility = View.INVISIBLE
            cameraButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_camera_off))

        }
    }

}


