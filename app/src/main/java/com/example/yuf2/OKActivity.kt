package com.example.yuf2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.yuf2.databinding.ActivityIdactivityBinding
import com.example.yuf2.databinding.ActivityOkactivityBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.User
import com.example.yuf2.dataclass.nickname
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class OKActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOkactivityBinding
    private lateinit var uid :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okactivity)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_okactivity)

        uid = intent.getStringExtra("presentuid").toString()
       getData(uid)
    }

    fun getData(uid:String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) =try {

                val item = dataSnapshot.getValue(User::class.java)

                binding.nickname.setText(item!!.nickname)

            }catch (e: Exception){

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.nickname.child(uid).addValueEventListener(postListener)

    }
}