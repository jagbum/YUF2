package com.example.yuf2.dataclass

import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database


class Database {

    companion object{

        val user = Firebase.database.getReference("user")
        val nickname = Firebase.database.getReference("nickname")
        val Board = Firebase.database.getReference("board")


    }
}