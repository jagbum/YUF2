package com.example.yuf2.Board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityWriteBoardBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.User
import com.example.yuf2.dataclass.bestPost
import com.example.yuf2.dataclass.post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class  WriteBoardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityWriteBoardBinding
    private lateinit var uid :String
    private lateinit var nickname :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_board)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_write_board)

        uid = intent.getStringExtra("presentuid").toString()

        binding.save.setOnClickListener {
            saveBoard(uid, nickname)
        }

        getData(uid)
    }

    fun saveBoard(uid:String, nickname: String){

        val title = binding.title.text.toString()
        val content = binding.content.text.toString()


        if(title!=null&&content!=null){

            val postKey = Database.Board.push().key.toString()

            Database.Board.child(postKey).setValue(post(title, content, nickname, uid, 0))//push
            Database.nickname.child(uid).child("MyBoard").child(postKey).setValue(bestPost(title, content, nickname, 0))
            finish()

        }else{
            val toast = Toast.makeText(this, "모든 항목을 채워주세요", Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    fun getData(uid:String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) =try {

                val item = dataSnapshot.getValue(User::class.java)

                nickname = item!!.nickname
            }catch (e: Exception){

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.nickname.child(uid).addValueEventListener(postListener)

    }

}