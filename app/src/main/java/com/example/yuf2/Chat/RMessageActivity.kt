package com.example.yuf2.Chat

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yuf2.R
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.Friend
import com.example.yuf2.dataclass.FriendNoti
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


class RMessageActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var listener: ValueEventListener
    var et_rmessage_message: EditText? = null
    var bt_rmessage_send: Button? = null
    var rv_rmessage: RecyclerView? = null
    var iv_rmessage_addfriend: ImageView? = null
    var iv_rmessage_exitmessage: ImageView? = null
    var tv_rmessage_othernickname: TextView? = null
    var chatid: String? = null
    var message_list: ArrayList<Message>? = java.util.ArrayList()
    var adapter: RMessageAdapter? = null


    companion object {
        var otherid: String? = null
        var othernickname: String? = null
        var mynickname: String? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rmessage)
        init()

        otherid = intent.getStringExtra("otherid")
        othernickname = intent.getStringExtra("othernickname")
        mynickname = intent.getStringExtra("mynickname")

        tv_rmessage_othernickname!!.text = othernickname

        iv_rmessage_addfriend!!.setOnClickListener(View.OnClickListener {
            saveFriend()
        })

        Database.randomChatQueue.child(auth.currentUser?.uid.toString()).removeValue()

        chatid = ChatTool.getChatid(otherid!!, auth.currentUser?.uid.toString())
        rv_rmessage = findViewById<View>(R.id.rv_rmessage) as RecyclerView


        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_rmessage!!.layoutManager = lm

        adapter = RMessageAdapter(message_list!!)
        rv_rmessage!!.adapter = adapter

        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                message_list!!.clear()
                for (i in snapshot.children) {
                    val item = i.getValue(Message::class.java)
                    message_list!!.add(item!!)
                }
                adapter!!.notifyDataSetChanged()
                rv_rmessage!!.smoothScrollToPosition(message_list!!.size + 1)
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        Database.randomChat.child(chatid!!).child("message").addValueEventListener(listener);

        bt_rmessage_send!!.setOnClickListener {
            val msg = Message(mynickname!!, et_rmessage_message!!.text.toString(), ChatTool.getCurrenttime()!!)
            Database.randomChat.child(chatid!!).child("message").push().setValue(msg)
            Database.randomChat.child(chatid!!).child("update").setValue(ChatTool.getCurrenttime()!!)
            Database.randomChat.child(chatid!!).child("last").setValue(et_rmessage_message!!.text.toString())

            et_rmessage_message!!.setText("")
            rv_rmessage!!.smoothScrollToPosition(message_list!!.size + 1)
        }
        iv_rmessage_exitmessage!!.setOnClickListener {
            Database.chat.child(chatid!!).child("message").removeEventListener(listener)
            finish()
        }


    }

    fun init() {
        et_rmessage_message = findViewById<View>(R.id.et_rmessage_message) as EditText
        bt_rmessage_send = findViewById<View>(R.id.bt_rmessage_send) as Button
        iv_rmessage_addfriend = findViewById<View>(R.id.iv_rmessage_addfriend) as ImageView
        tv_rmessage_othernickname = findViewById<View>(R.id.tv_rmessage_othernickname) as TextView
        iv_rmessage_exitmessage = findViewById<View>(R.id.iv_rmessage_exitmessage) as ImageView

    }

    fun saveFriend(){

        Database.nickname.child(auth.currentUser?.uid.toString()).child("Friend").orderByChild("uid").equalTo(
            otherid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        Database.nickname.child(auth.currentUser?.uid.toString()).child("Friend").child(otherid!!).setValue(Friend(
                            othernickname!!, otherid!!))
                        Database.nickname.child(otherid!!).child("notification").child("friendnoti").child(auth.currentUser?.uid.toString()).setValue(
                            FriendNoti(mynickname!!,auth.currentUser?.uid.toString())
                        )
                        Toast.makeText(applicationContext, "친구에 추가하였습니다.", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(applicationContext, "친구에 추가된 사람입니다.", Toast.LENGTH_SHORT).show()                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
    }
}
