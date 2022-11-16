package com.example.yuf2.Board

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.yuf2.Home.BestBoardAdapter
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityBestBoardBinding
import com.example.yuf2.databinding.ActivityMyPostBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.bestPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class myPostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyPostBinding
    private val myBoardData = mutableListOf<bestPost>()
    private val myBoardKeyList = mutableListOf<String>()
    private lateinit var myBoardAdpater: MyBoardAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var uid :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_post)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_post)
        uid = intent.getStringExtra("currentUID").toString()

        myBoardAdpater = MyBoardAdapter(myBoardData)

        binding.MyPost.adapter = myBoardAdpater

        binding.MyPost.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, ReadBoardActivity::class.java)
            intent.putExtra("key", myBoardKeyList[i])
            intent.putExtra("currentUID", auth.currentUser?.uid.toString())
            startActivity(intent)
        }

        getMyBoard(uid)

    }

    fun getMyBoard(myUID:String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                myBoardData.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(bestPost::class.java)
                    myBoardData.add(item!!)
                    myBoardKeyList.add(dataModel.key.toString())

                }
                myBoardKeyList.reverse()
                myBoardData.reverse()
                myBoardAdpater.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        FirebaseDatabase.getInstance().getReference("nickname").child(myUID).child("MyBoard").addValueEventListener(postListener)
    }
}