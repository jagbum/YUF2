package com.example.yuf2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.yuf2.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this,R.layout.activity_join)

        binding.join.setOnClickListener{
            val name = binding.name.text.toString()
            val email = binding.ID.text.toString()
            val password = binding.PW.text.toString()
            val confirm = binding.PWCheck.text.toString()
            val uid = auth.currentUser?.uid.toString()

            var checkID = true

            if(!password.equals(confirm)){
                Toast.makeText(this, "비밀번호가 틀립니다. \n같은 비밀번호르 입력해주세요.", Toast.LENGTH_LONG).show()
                checkID = false
            }

            if(checkID){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                                Database.user.push().setValue(name, email,password, uid)
                                Toast.makeText(this,"회원가입을 완료했습니다!\n 로그인 해주세요!", Toast.LENGTH_LONG).show()


                            val intent = Intent(this,LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)


                        } else {
                            Toast.makeText(this,"다시 시도해주세요!", Toast.LENGTH_LONG).show()

                        }
                    }
            }
        }
    }
}