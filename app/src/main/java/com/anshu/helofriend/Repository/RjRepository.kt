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
                    var _userList : ArrayList<RjUser>
                    _userList= ArrayList<RjUser>()
                    users?.let { _userList.add(it) }

//                    val _userList : List<RjUser> = snapshot.children.map { dataSnapshot ->

//                        dataSnapshot.getValue(RjUser::class.java)!!

//                    }

                    userList.postValue(_userList)

                }
                catch (e : Exception){
                        e.printStackTrace()

                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }}