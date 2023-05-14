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
import com.anshu.Helofriend.databinding.FragmentHomeBinding
import com.anshu.helofriend.Activity.CategoriesActivity
import com.anshu.helofriend.Activity.WalletActivity
import com.anshu.helofriend.Adapters.ImageSliderAdapter
import com.anshu.helofriend.Adapters.RjAdapter
import com.anshu.helofriend.Model.RjUser
import com.anshu.helofriend.Model.User
import com.anshu.helofriend.ViewModel.RjViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference

    var userList: ArrayList<RjUser> = ArrayList()
    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: RjViewModel

    lateinit var rJadapter: RjAdapter
    private var gender = ""

    private val storage = Firebase.storage
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
//        Firebase.auth.signOut()



/*
        val images = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3
        )
        imageSliderAdapter = ImageSliderAdapter(imageNames)
        binding.viewPager.adapter = imageSliderAdapter
*/

        /*var firebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser = firebaseAuth.getCurrentUser()!!
        var  userKey = user.uid*/
        // Get a reference to the Firebase Realtime Database
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        var nodeName = requireActivity().intent.getStringExtra("fullName")
        Log.d("NodeName", nodeName.toString())
// Specify the path to the data you want to filter
        val dataReference: DatabaseReference = databaseReference.child("User")

// Apply a query to filter the data
        val query: Query = dataReference.orderByChild(nodeName.toString())

// Add a ValueEventListener to read the data
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called when the data is changed or initially loaded

                // Retrieve the data from the dataSnapshot
//                val value = dataSnapshot.children
//
//                var userArr = dataSnapshot.toString().split("{")
//                var coinsArr = userArr[3].split("=")
//                Log.d("Userreader", coinsArr[3].split(",")[0])
//                binding.addCoins.text = coinsArr[3].split(",")[0]
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Faliure", databaseError.toString())
                // This method is called if the data retrieval is canceled or fails
                // Handle the error as needed
            }
        })
        /*  var firebaseAuth = FirebaseAuth.getInstance()
          var mDatabase = FirebaseDatabase.getInstance()
          var mDb = mDatabase.getReference()
           val user: FirebaseUser = firebaseAuth.getCurrentUser()!!
         var  userKey = user.uid

           mDb.child("User").child(userKey).addValueEventListener(object : ValueEventListener {
               override fun onDataChange(dataSnapshot: DataSnapshot) {
                   val coins = dataSnapshot.child("coins").getValue(String::class.java)
                   Log.d(TAG, "Coins Searchinggg: $dataSnapshot")
               }

               override fun onCancelled(databaseError: DatabaseError) {}
           })*/


        binding.btnOnline.setOnClickListener {

            activity?.let {
                val intent = Intent(it, CategoriesActivity::class.java)
                it.startActivity(intent)
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

        /* val coinRef = FirebaseDatabase.getInstance().getReference("User")
         coinRef.child("coins").addValueEventListener(object : ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {
                 // Retrieve the data and handle updates
                 val user = dataSnapshot.getValue(User::class.java)
                 // Do something with the user data
                 binding.addCoins.text = user!!.coins.toInt().toString()
                 Log.d(TAG, "COINS")
             }

             override fun onCancelled(databaseError: DatabaseError) {
                 // Handle the error
             }
         })*/



        // Setting the Adapter with the recyclerview
//        recyclerview.adapter = adapter

     /*   viewModel = ViewModelProvider(this)[RjViewModel::class.java]


        viewModel.allUsers.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
           
            binding.recyclerView.setHasFixedSize(true)
//            adapter.updateUserList(it)
            binding.recyclerView.adapter = rJadapter
            rJadapter = RjAdapter(userList)
            userList.clear()
            userList.addAll(it)
            rJadapter.notifyDataSetChanged()

        })
        getData()*/
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
//                binding.tvName.text = user.fullName.toString()
                mDatabase.push()

            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }
    }


}