package com.anshu.helofriend.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anshu.helofriend.Model.User
import com.anshu.Helofriend.databinding.ActivityRegisterBinding
import com.google.firebase.database.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference
    private var gender = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.ContinueRegi.setOnClickListener {
            saveData()
            startActivity(Intent(this, dashboard::class.java))
            finish()

        }
        database = FirebaseDatabase.getInstance().getReference("User")

        binding.radioButton1.setOnClickListener {
            gender = "male"
            binding.radioButton2.isChecked = false
        }
        binding.radioButton2.setOnClickListener {
            gender = "female"
            binding.radioButton1.isChecked = false

        }


    }

    private fun saveData() {

        val fullName = binding.fullNameRegi.text.toString()
        val email = binding.emailRegi.text.toString()
        val DOB = binding.DOBRegi.text.toString()
        val PhoneNumber = binding.PhoneRegi.text.toString()
        val radioGroup = binding.radioGroup.checkedRadioButtonId.toString()

        database = FirebaseDatabase.getInstance().getReference("User")
        val gender = gender
        val User = User(fullName, email, DOB, PhoneNumber, gender)
//        database.push().child("gender").setValue(radioGroup)
        database.child("12345").setValue(User).addOnSuccessListener {
            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {

            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
        binding.fullNameRegi.text.clear()
        binding.emailRegi.text.clear()
        binding.DOBRegi.text.clear()
        binding.PhoneRegi.text.clear()


    }


}
