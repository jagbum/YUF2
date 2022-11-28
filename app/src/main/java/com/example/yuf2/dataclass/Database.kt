package com.example.yuf2.dataclass

import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database


class Database {

    companion object{

        val user = Firebase.database.getReference("user")
        val nickname = Firebase.database.getReference("nickname")
        val Board = Firebase.database.getReference("board")
        val BestBoard = Firebase.database.getReference("bestBoard")
        val comment = Firebase.database.getReference("comment")
        val chat = Firebase.database.getReference("chat")
        val randomChat = Firebase.database.getReference("randomChat")
        val randomChatQueue = Firebase.database.getReference("randomChatQueue")
        val noti = Firebase.database.getReference("noti")

    }
}