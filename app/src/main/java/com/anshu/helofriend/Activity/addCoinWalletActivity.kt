package com.anshu.helofriend.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anshu.Helofriend.databinding.ActivityAddCoinWalletBinding
import com.anshu.helofriend.Model.Wallet
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class addCoinWalletActivity : AppCompatActivity(), PaymentResultListener {
    lateinit var binding: ActivityAddCoinWalletBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCoinWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)


        val addCoins = binding.addCoins
        val addMoney = binding.addMoney
        val coins = intent?.getStringExtra("coins")
        val price = intent?.getStringExtra("price")

        addCoins.text = coins.toString()
        addMoney.text = price.toString()

        binding.payBtn.setOnClickListener {
            val amount = binding.addMoney.text.toString().trim()
            if (amount.isEmpty()) return@setOnClickListener

            startPayment(amount.toInt())

        }
    }

    private fun startPayment(amount: Int) {

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_1rO4GYhFp5Hc08")
        try {
            val options = JSONObject()
            options.put("name", "Razorpay Integration")
            options.put("description", "Adding your money")

            //You can omit the image option to fetch the image from dashboard
            //   options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")

            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")

            /** Generated from backend **/
            //      options.put("order_id", "order_DBJOWzybf0sJbb")

            /** Pass in paise in INR  ( Example  Rs 5 = 500 paise ) **/
            options.put("amount", "${(amount * 100)}")//pass amount in currency subunits
            options.put("prefill.email", "anshuchaurasiya9@gmail.com")
            options.put("prefill.contact", "+919442009211")

            checkout.open(this, options)

        }
        catch (e: Exception) {

            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG)
                .show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {

        binding.showStatus.text = p0
        binding.showStatus.setTextColor(Color.GREEN)
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
        addCoins()
    }

    private fun addCoins() {
        database = FirebaseDatabase.getInstance().getReference("Wallet")
        val coins = binding.addCoins.text.toString()
        val money = binding.addMoney.text.toString()
        val wallet = Wallet(coins, "", money)

        database.child("Coins").setValue(wallet).addOnSuccessListener {

            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)


        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d(TAG, "onPaymentError: $p0")
        Log.d(TAG, "onPaymentError: $p1")
        binding.showStatus.text = p1
        binding.showStatus.setTextColor(Color.RED)

        Toast.makeText(this, "Payment Not Successful", Toast.LENGTH_SHORT).show()
    }

}
