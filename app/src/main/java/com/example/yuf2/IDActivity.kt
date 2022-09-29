package com.example.yuf2

import android.content.Intent
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
    private lateinit var name :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idactivity)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_idactivity)

        studentID = intent.getStringExtra("studentID").toString()
        name = intent.getStringExtra("name").toString()

        FindID(studentID, name)

        binding.OK.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    fun FindID(studentID:String, name:String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) =try {
                    val item = dataSnapshot.getValue(User::class.java)

                    if(name.equals(item!!.name)){
                        binding.id.setText(item!!.id)
                    }else{
                        binding.id.setText("잘못입력하였습니다.")
                    }

                }catch (e: Exception){
                    binding.id.setText("잘못입력하였습니다.")
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.user.child(studentID).addValueEventListener(postListener)

    }
}