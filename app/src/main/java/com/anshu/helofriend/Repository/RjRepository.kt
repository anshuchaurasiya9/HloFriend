package com.anshu.helofriend.Repository

import androidx.lifecycle.MutableLiveData
import com.anshu.helofriend.Model.RjUser
import com.google.firebase.database.*

class RjRepository  {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Rjusers")

    @Volatile private var INSTANCE : RjRepository?= null

    fun getInstance() : RjRepository {
        return INSTANCE ?: synchronized(this){

            val instance = RjRepository()
            INSTANCE = instance
            instance
        }


    }


    fun loadUsers(userList : MutableLiveData<List<RjUser>>){

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {
                    var users=snapshot.getValue(RjUser::class.java)
                    var _allUsers : ArrayList<RjUser> = ArrayList<RjUser>()
                    users?.let { _allUsers.add(it) }

//                    val _userList : List<RjUser> = snapshot.children.map { dataSnapshot ->

//                        dataSnapshot.getValue(RjUser::class.java)!!

//                    }

                    userList.postValue(_allUsers)

                }
                catch (e : Exception){
                        e.printStackTrace()

                }


            }

            override fun onCancelled(error: DatabaseError) {
            }


        })

    }}