package com.anshu.helofriend.fragment

import android.R
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.anshu.Helofriend.databinding.FragmentHomeBinding
import com.anshu.helofriend.Activity.CategoriesActivity
import com.anshu.helofriend.Activity.WalletActivity
import com.anshu.helofriend.Adapters.RjAdapter
import com.anshu.helofriend.Model.RjUser
import com.anshu.helofriend.Model.User
import com.anshu.helofriend.Model.UsersMoney
import com.anshu.helofriend.Model.Wallet
import com.anshu.helofriend.ViewModel.RjViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference

    var userList: ArrayList<RjUser> = ArrayList()
    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: RjViewModel

    lateinit var adapter: RjAdapter
    private var gender = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
//        Firebase.auth.signOut()



        binding.btnOnline.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CategoriesActivity::class.java)
                it.startActivity(intent)

              /*intent.getSerializableExtra("male") as  User
             intent.getSerializableExtra("female") as User
                val genderId = user.gender

                if (genderId == "male") {
                    binding.onlineCard.visibility = GONE
                } else if (genderId == "female") {
                    binding.onlineCard.visibility = VISIBLE
                } else {
                    Toast.makeText(requireContext(), "gender is others", Toast.LENGTH_SHORT).show()
                }*/
//                val gender = when (genderId) {
//                    R.id.radioMale -> "Male"
//                    R.id. -> "Female"
//                    else -> "Other"

            }
        }



        database = FirebaseDatabase.getInstance().getReference("User")
        val coins = binding.addCoins.text.toString()
//        val user = UsersMoney(coins)
//        database.child(coins).setValue(user)


//        binding.profile.setOnClickListener{
////            requireFragmentManager().beginTransaction().replace(R.id.list_container,
////                ProfileFragment()).commit()
//        }
        binding.addCoins.setOnClickListener {
            startActivity(Intent(requireContext(), WalletActivity::class.java))

        }

        val coinRef = FirebaseDatabase.getInstance().getReference("Wallet")
        coinRef.child("coins").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Retrieve the data and handle updates
                val user = dataSnapshot.getValue(Wallet::class.java)
                // Do something with the user data
//                binding.addCoins.text = user!!.coins.toInt().toString()
                Log.d(TAG, "COINS")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }
        })


        adapter = RjAdapter(userList)
        binding.recyclerView.setHasFixedSize(true)
        // Setting the Adapter with the recyclerview
//        recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this).get(RjViewModel::class.java)
        viewModel.allUsers.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recyclerView.adapter = adapter
//            adapter.updateUserList(it)
            userList.clear()
            userList.addAll(it)
            adapter.notifyDataSetChanged()

        })
        getData()
        return binding.root
    }

    lateinit var user: User
    private fun getData() {
        val mDatabase = Firebase.database.reference
        if (arguments != null) {
            mDatabase.child("User").child("fullName").get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                user = it.getValue(User::class.java)!!
                Log.e("Home", "getData: Gender" + user.gender)
                binding.tvName.text = user.fullName.toString()
                mDatabase.push()

            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }
    }


}