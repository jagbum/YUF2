package com.example.yuf2.Chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityChatWaitingBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.comment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ChatWaitingActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding : ActivityChatWaitingBinding

    private val waitList = mutableListOf<String>()
    private lateinit var otherUID: String
    private var waitListSize: Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_waiting)

        insertQueue()
        chatWait()
    }

    override fun onBackPressed(){
        Database.randomChatQueue.child(auth.currentUser?.uid.toString()).removeValue()
        finish()
    }

    fun insertQueue(){
        Database.randomChatQueue.child(auth.currentUser?.uid.toString()).setValue(auth.currentUser?.uid.toString())
    }

    fun chatWait(){
        val waitListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                waitList.clear()
                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(String::class.java)
                    waitList.add(item!!)
                }
                if(waitList.size != 1)
                {
                    waitList.remove(auth.currentUser?.uid.toString())
                    otherUID = waitList.random()
                    match()
                    Database.randomChatQueue.removeEventListener(this)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        Database.randomChatQueue.addValueEventListener(waitListener)

    }

    fun match(){
        val intent = Intent(this, RMessageActivity::class.java)
        Database.nickname.addListenerForSingleValueEvent((object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ChatTool.createRandomChat(applicationContext,auth.currentUser?.uid.toString(),otherUID)
                intent.putExtra("otherid", otherUID)
                intent.putExtra("othernickname", snapshot.child(otherUID).child("nickname").getValue(String::class.java))
                intent.putExtra("mynickname", snapshot.child(auth.currentUser?.uid.toString()).child("nickname").getValue(String::class.java))
                startActivity(intent)
            }
            override fun onCancelled(error: DatabaseError) {}
        }))




    }


}