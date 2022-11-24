package com.example.yuf2.Freind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.yuf2.Board.CommentAdapter
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityProfileBinding
import com.example.yuf2.databinding.ActivityReadBoardBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.User
import com.example.yuf2.dataclass.post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var key :String
    private lateinit var nickname :String
    private lateinit var state :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        key = intent.getStringExtra("key").toString()

        getData()

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


}