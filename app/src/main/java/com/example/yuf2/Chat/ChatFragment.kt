package com.example.yuf2.Chat

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yuf2.R
import com.example.yuf2.databinding.FragmentChatBinding
import com.example.yuf2.dataclass.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.*


class ChatFragment : Fragment() {
    var rv_chat: RecyclerView? = null
    var dialogView: View? = null
    var et_chat_tid: EditText? = null
    private lateinit var adapter: ChatAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : FragmentChatBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        ChatTool.chatFragmentContext = getContext()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        val v = inflater.inflate(R.layout.fragment_chat, container, false)
        rv_chat = v.findViewById<View>(R.id.rv_chat) as RecyclerView
        auth = Firebase.auth
        adapter = ChatAdapter()
        Database.nickname.child(auth.currentUser?.uid.toString()).child("chat").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ChatTool.chatid_list.clear()
                ChatTool.chat_list.clear()
                for (i in snapshot.children) {
                    ChatTool.chatid_list.add(i.key!!)
                }
                Database.chat.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (i in ChatTool.chatid_list) {
                            val j = snapshot.child(i!!)
                            val temp_update = j.child("update").getValue(String::class.java)
                            val temp_last = j.child("last").getValue(String::class.java)
                            val temp_frontid = j.child("frontid").getValue(String::class.java)
                            val temp_frontname = j.child("frontnickname").getValue(String::class.java)
                            val temp_endid = j.child("endid").getValue(String::class.java)
                            val temp_endname = j.child("endnickname").getValue(String::class.java)
                            ChatTool.chat_list.add(
                                Chat(
                                    i, temp_update ?: "", temp_last ?: "",
                                    temp_frontid!!,
                                    temp_frontname!!,
                                    temp_endid!!,
                                    temp_endname!!
                                )
                            )
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        binding.rvChat.adapter = adapter
        binding.rvChat.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.setting.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_settingFragment)
        }

        binding.notification.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_notiFragment)
        }

        binding.friend.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_friendFragment)
        }

        binding.board.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_boardFragment)
        }

        binding.home.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_homeFragment)
        }

        binding.write.setOnClickListener{
            dialogView = View.inflate(context, R.layout.dialog_chat_create, null) as View
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("상대방 아이디를 입력하세요.")
            dialog.setView(dialogView)
            dialog.setPositiveButton(
                "확인"
            ) { dialogInterface, i ->
                et_chat_tid =
                    dialogView!!.findViewById<View>(R.id.et_chat_tid) as EditText
                ChatTool.createChat(auth.currentUser?.uid.toString(), et_chat_tid!!.text.toString())
            }
            dialog.setNegativeButton("취소", null)
            dialog.show()
        }

        return binding.root
    }


/*
*/
}