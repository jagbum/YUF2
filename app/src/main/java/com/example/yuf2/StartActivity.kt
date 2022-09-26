package com.example.yuf2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.Intent


class StartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        auth = Firebase.auth

        if (auth.currentUser?.uid == null) {
            Handler().postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
            }, 1000)
        } else {
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
            }, 1000)

        }
    }
}
