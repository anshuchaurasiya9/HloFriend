package com.anshu.helofriend.Model

data class User(
    val fullName: String? = "", val email: String? = "",
    val dob: String? = "", val phoneNumber: String? = "",
    val gender: String? = "", val coins: Int = 0,
    val discount: Int = 0,
    val price: Int = 0
)
