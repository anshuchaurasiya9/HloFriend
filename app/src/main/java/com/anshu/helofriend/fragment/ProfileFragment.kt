package com.anshu.helofriend.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anshu.Helofriend.databinding.FragmentProfileBinding
import com.anshu.helofriend.Activity.WalletActivity
import com.anshu.helofriend.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

 lateinit var user: User


       FirebaseDatabase.getInstance().getReference("User")
        binding.tvName.text = user.fullName.toString()
        binding.tvEmail.text = user.email.toString()
        binding.tvGender.text = user.gender.toString()
        binding.tvMobile.text = user.phoneNumber.toString()

        binding.walletImg.setOnClickListener {
            activity?.let { it ->
                val intent = Intent(it, WalletActivity::class.java)
                it.startActivity(intent)


                binding.singOutBtn.setOnClickListener {
                    if (auth != null) {
                        auth.signOut()
                        activity?.let { activity ->
                            val i = Intent(activity, WalletActivity::class.java)
                            activity.startActivity(i)
                        }

//                        Toast.makeText(this, "You are not signing off", Toast.LENGTH_SHORT).show()


                    }
                }

            }
        }



        return binding.root
    }


}