package com.example.yuf2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.yuf2.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.databinding.DataBindingUtil


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        binding.LoginBtn.setOnClickListener{
            val id = binding.id.text.toString()
            val password = binding.password.text.toString()

            if(id != null&&password != null) {
                auth.signInWithEmailAndPassword(id, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "아이디나 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.JoinBtn.setOnClickListener{
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

    }

    private var i=0
    override fun onBackPressed(){
        if(i==2){
            ActivityCompat.finishAffinity(this)
        }
        i += 1

        Toast.makeText(this,"종료하시려면 더블클릭해주세요", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable{ i=0 },2000)
    }
}