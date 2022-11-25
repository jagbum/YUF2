package com.example.yuf2.Chat

import android.content.Context
import android.widget.Toast
import com.example.yuf2.dataclass.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class ChatTool {

    companion object
    {
        private var auth : FirebaseAuth = Firebase.auth
        var check_make_chat = false
        var chatFragmentContext: Context? = null
        public var chatid_list = ArrayList<String>()
        public var chat_list = ArrayList<Chat>()
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
                        val front_nickname: String?
                        val end_nickname: String?
                        front_nickname = snapshot.child(front).child("nickname").getValue(String::class.java)
                        end_nickname = snapshot.child(end).child("nickname").getValue(String::class.java)
                        Database.chat.child(chatid).child("frontid").setValue(front)
                        Database.chat.child(chatid).child("frontnickname").setValue(front_nickname)
                        Database.chat.child(chatid).child("endid").setValue(end)
                        Database.chat.child(chatid).child("endnickname").setValue(end_nickname)
                        Database.chat.child(chatid).child("update").setValue(getCurrenttime())

                        Database.nickname.child(front).child("chat").child(chatid).setValue(getCurrenttime());
                        Database.nickname.child(end).child("chat").child(chatid).setValue(getCurrenttime());

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
        fun getChangetime(update: String): String? {
            val now = System.currentTimeMillis()
            val date = Date(now)
            val current = SimpleDateFormat("yyyyMMddhhmmss").format(date)
            val ld = update.toLong()
            val cd = current.toLong()
            val diff = cd - ld
            return if (diff < 1000000) "오늘" else if (diff < 2000000) "어제" else update.substring(0, 4) + " - " + update.substring(5, 7) + " - " + update.substring(7, 9)
        }

    }
}