package com.example.yuf2.Board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityEditPostBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.bestPost
import com.example.yuf2.dataclass.post
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class EditPostActivity : AppCompatActivity() {

    var database = Firebase.database
    private lateinit var binding : ActivityEditPostBinding
    private lateinit var key :String
    private lateinit var uid :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_post)

        key = intent.getStringExtra("key").toString()

        binding.save.setOnClickListener {
            edit(key)
        }


        getData(key)

    }

    fun getData(key:String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val item = dataSnapshot.getValue(post::class.java)

                    binding.title.setText(item!!.title)
                    binding.content.setText(item!!.content)
                    uid = item.uid

                }catch (e: Exception){

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.Board.child(key).addValueEventListener(postListener)
    }

    fun edit(key: String){

        Database.Board.child(key).child("title").setValue(binding.title.text.toString())
        Database.Board.child(key).child("content").setValue(binding.content.text.toString())
        Database.nickname.child(uid).child("MyBoard").child(key).child("title").setValue(binding.title.text.toString())

        Database.BestBoard.orderByChild("key").equalTo(key)
           .addListenerForSingleValueEvent(object : ValueEventListener {
               override fun onDataChange(dataSnapshot: DataSnapshot) {
                   if (!dataSnapshot.exists()) {

                   } else {
                       Database.BestBoard.child(key).child("title").setValue(binding.title.text.toString())
                       Database.BestBoard.child(key).child("content").setValue(binding.content.text.toString())
                   }
               }

               override fun onCancelled(databaseError: DatabaseError) {

               }
           })


        finish()

    }



}