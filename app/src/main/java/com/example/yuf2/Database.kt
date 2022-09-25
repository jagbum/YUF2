package com.example.yuf2

import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database


class Database {

    companion object{

        val user = Firebase.database.getReference("user")



    }
}