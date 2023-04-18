package com.anshu.helofriend.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshu.helofriend.Adapters.RjAdapter
import com.anshu.helofriend.Model.RjUser
import com.anshu.helofriend.Model.User
import com.anshu.helofriend.ViewModel.RjViewModel
import com.anshu.Helofriend.databinding.FragmentHomeBinding

import com.anshu.helofriend.Activity.CategoriesActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference


    var userList: ArrayList<RjUser> = ArrayList()
    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: RjViewModel

    private lateinit var userRecyclerView: RecyclerView
    lateinit var adapter: RjAdapter
    private var gender = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
//        Firebase.auth.signOut()

        binding.onlineTv.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CategoriesActivity::class.java)
                it.startActivity(intent)
        }}



        adapter= RjAdapter(userList)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.recyclerView.adapter = adapter

        val recyclerview = binding.recyclerView
        recyclerview.setHasFixedSize(true)
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this).get(RjViewModel::class.java)


        viewModel.allUsers.observe(viewLifecycleOwner, Observer {

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
        if(arguments != null){
        mDatabase.child("User").child("fullName").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            user=it.getValue(User::class.java)!!
            Log.e("Home", "getData: Gender"+user.gender)
            binding.tvName.text="Hi "+user.fullName

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }}


}