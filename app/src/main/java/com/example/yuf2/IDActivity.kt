package com.example.yuf2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.yuf2.databinding.ActivityIdactivityBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class IDActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIdactivityBinding
    private lateinit var studentID :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idactivity)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_idactivity)

        studentID = intent.getStringExtra("studentID").toString()

        getData(studentID)

    }

    fun getData(studentID:String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) =try {

                    val item = dataSnapshot.getValue(User::class.java)

                    binding.id.setText(item!!.id)

                }catch (e: Exception){

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.user.child(studentID).addValueEventListener(postListener)

    }
}