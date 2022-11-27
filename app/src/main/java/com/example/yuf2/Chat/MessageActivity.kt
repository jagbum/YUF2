package com.example.yuf2.Chat

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yuf2.R
import com.example.yuf2.dataclass.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class MessageActivity : Activity() {
    private var auth: FirebaseAuth = Firebase.auth
    var et_message_message: EditText? = null
    var bt_message_send: Button? = null
    var rv_message: RecyclerView? = null
    var chatid: String? = null
    var message_list: ArrayList<Message>? = java.util.ArrayList()
    var adapter: MessageAdapter? = null

    companion object {
        var otherid: String? = null
        var othernickname: String? = null
        var mynickname: String? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        init()

        otherid = intent.getStringExtra("otherid")
        othernickname = intent.getStringExtra("othernickname")
        mynickname = intent.getStringExtra("mynickname")

        chatid = ChatTool.getChatid(otherid!!, auth.currentUser?.uid.toString())
        message_list
        rv_message = findViewById<View>(R.id.rv_message) as RecyclerView

        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_message!!.layoutManager = lm

        adapter = MessageAdapter(message_list!!)
        rv_message!!.adapter = adapter

        Database.chat.child(chatid!!).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    message_list!!.clear()
                    for (i in snapshot.children) {
                        val item = i.getValue(Message::class.java)
                        message_list!!.add(item!!)
                    }
                    adapter!!.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {}
            })

        bt_message_send!!.setOnClickListener {
            val msg = et_message_message!!.text.toString()
            Database.chat.child(chatid!!).child("message").child(ChatTool.getCurrenttime()!!)
                .child("msg").setValue(msg)
            Database.chat.child(chatid!!).child("message").child(ChatTool.getCurrenttime()!!)
                .child("nickname").setValue(mynickname)
            Database.chat.child(chatid!!).child("update").setValue(ChatTool.getCurrenttime()!!)
            Database.chat.child(chatid!!).child("last").setValue(msg)

            et_message_message!!.setText("")
            rv_message!!.smoothScrollToPosition(message_list!!.size + 1)


        }
    }

    fun init() {
        et_message_message = findViewById<View>(R.id.et_message_message) as EditText
        bt_message_send = findViewById<View>(R.id.bt_message_send) as Button
    }
}
