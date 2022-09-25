package com.example.yuf2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }
}