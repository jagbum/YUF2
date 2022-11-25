package com.example.yuf2.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityUserInfoBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.Database.Companion.nickname
import com.example.yuf2.dataclass.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class UserInfoActivity : AppCompatActivity() {

    var database = Firebase.database

    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var key: String
    private lateinit var uid: String
//    private lateinit var nickname :String
//    private lateinit var state :String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        uid = intent.getStringExtra("currentUID").toString()
        key = intent.getStringExtra("key").toString()


        binding.save.setOnClickListener {
            edit()
        }

        getData()

    }

    fun getData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val item = dataSnapshot.getValue(User::class.java)

//                    nickname = item!!.nickname
                    //  state = item!!.state
//                    binding.name.setText(item!!.nickname)
                    binding.state.setText(item!!.state)


                } catch (e: Exception) {

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.nickname.child(uid).addValueEventListener(postListener)
    }

    fun edit() {

        Database.nickname.child(uid).child("state").setValue(binding.state.text.toString())

        finish()

    }


}
