package com.example.yuf2.Chat

import android.content.Context
import android.widget.Toast
import com.example.yuf2.dataclass.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class ChatTool {
    private lateinit var auth: FirebaseAuth
    var check_make_chat = false
    companion object
    {
        var chatFragmentContext: Context? = null
    }
    fun createChat(str1: String, str2: String) {
        val flag = str1.compareTo(str2)
        check_make_chat = false
        val front: String
        val end: String
        if (flag == 0) {
            Toast.makeText(chatFragmentContext, "채팅방 생성 실패 !!", Toast.LENGTH_SHORT).show()
            return
        } else if (flag < 0) {
            front = str1
            end = str2
        } else {
            front = str2
            end = str1
        }
        val chatid = front + end
        Database.nickname.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val is_front = snapshot.child(front).hasChildren()
                val is_end = snapshot.child(end).hasChildren()
                if (is_front && is_end) {
                    val front_name: String?
                    val end_name: String?
                    front_name = snapshot.child(front).child("name").getValue(String::class.java)
                    end_name = snapshot.child(end).child("name").getValue(String::class.java)
                    Database.chat.child(chatid).child("front").setValue(front)
                    Database.chat.child(chatid).child("end").setValue(end)
                    Database.chat.child(chatid).child("update").setValue(getCurrenttime())

                    if (auth.currentUser?.uid.toString().equals(front)) end_name else front_name

                    Database.nickname.child(front).child("chat").child(chatid).setValue(if (auth.currentUser?.uid.toString().equals(front)) end_name else front_name);
                    Database.nickname.child(end).child("chat").child(chatid).setValue(if (auth.currentUser?.uid.toString().equals(end)) front_name else end_name);

                    Toast.makeText(chatFragmentContext, "채팅방 생성 성공 !!", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(chatFragmentContext, "채팅방 생성 실패 !!", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    fun getCurrenttime(): String? {
        val now = System.currentTimeMillis()
        val date = Date(now)
        return SimpleDateFormat("yyyyMMddhhmmss").format(date)
    }


}