package com.anshu.helofriend.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anshu.Helofriend.databinding.FragmentProfileBinding
import com.anshu.helofriend.Activity.WalletActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private lateinit var database: DatabaseReference
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)


        val database = FirebaseDatabase.getInstance()


        val usersRef = database.getReference("User")


        binding.walletImg.setOnClickListener {
            activity?.let {
                val intent = Intent(it, WalletActivity::class.java)
                it.startActivity(intent)
                auth = Firebase.auth

//                binding.singOutBtn.setOnClickListener {
//                }


            }
        }



        return binding.root
    }


}