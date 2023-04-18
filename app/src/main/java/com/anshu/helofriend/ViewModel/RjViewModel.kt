package com.anshu.helofriend.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anshu.helofriend.Model.RjUser
import com.anshu.helofriend.Repository.RjRepository

class RjViewModel : ViewModel() {
    private val repository : RjRepository = RjRepository().getInstance()
    private val _allUsers = MutableLiveData<List<RjUser>>()
    val allUsers : LiveData<List<RjUser>> = _allUsers


    init {

        repository.loadUsers(_allUsers)

    }
}