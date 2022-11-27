package com.example.yuf2.Freind

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.yuf2.Board.CommentAdapter
import com.example.yuf2.Chat.ChatTool
import com.example.yuf2.Chat.MessageActivity
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityProfileBinding
import com.example.yuf2.databinding.ActivityReadBoardBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.Friend
import com.example.yuf2.dataclass.User
import com.example.yuf2.dataclass.post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding : ActivityProfileBinding
    private lateinit var key :String
    private lateinit var nickname :String
    private lateinit var state :String
    private lateinit var uid :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        key = intent.getStringExtra("key").toString()
        uid = intent.getStringExtra("currentUID").toString()

        binding.deleteFriend.setOnClickListener {
            removeFriend()
        }

        getData()
        getimage()

    }

    fun getData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val item = dataSnapshot.getValue(User::class.java)

                    nickname = item!!.nickname
                    state = item!!.state

                    binding.name.text = item!!.nickname
                    binding.state.text = item!!.state


                }catch (e: Exception){

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.nickname.child(key).addValueEventListener(postListener)

    }

    fun getimage(){

        val profileImg = Firebase.storage.reference.child(key+ ".jpg")

        val imageView = binding.image

        profileImg.downloadUrl.addOnCompleteListener(OnCompleteListener{ task->
            if(task.isSuccessful){
                Glide.with(this).load(task.result).into(imageView)
            }else{

            }
        })
    }

    fun removeFriend(){


        Database.nickname.child(uid).child("Friend").child(key).removeValue()


        finish()
    }

    fun startChat(){
        val intent = Intent(this, MessageActivity::class.java)
        Database.nickname.addListenerForSingleValueEvent((object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ChatTool.createChat(applicationContext,uid, key)
                intent.putExtra("otherid", key)
                intent.putExtra("othernickname", snapshot.child(key).child("nickname").getValue(String::class.java))
                intent.putExtra("mynickname", snapshot.child(uid).child("nickname").getValue(String::class.java))
                startActivity(intent)
            }
            override fun onCancelled(error: DatabaseError) {}
        }))
    }

}