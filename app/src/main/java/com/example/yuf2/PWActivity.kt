package com.example.yuf2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.yuf2.databinding.ActivityPwactivityBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class PWActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPwactivityBinding
    private lateinit var studentID :String
    private lateinit var name :String
    private lateinit var id :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwactivity)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_pwactivity)

        studentID = intent.getStringExtra("studentID").toString()
        name = intent.getStringExtra("name").toString()
        id = intent.getStringExtra("id").toString()

        FindPW(studentID, name, id)

        binding.OK.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun FindPW(studentID:String, name:String, id:String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) =try{
                val item = dataSnapshot.getValue(User::class.java)

                if(name.equals(item!!.name)&&id.equals(item!!.id)){
                    binding.pw.setText(item!!.password)
                }else{
                    binding.pw.setText("잘못입력하였습니다.")
                }

            }catch (e: Exception){

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.user.child(studentID).addValueEventListener(postListener)

    }
}