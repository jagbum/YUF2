package com.example.yuf2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.yuf2.databinding.ActivityLoginBinding
import com.example.yuf2.databinding.ActivitySearchIdactivityBinding

class SearchIDActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchIdactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_idactivity)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_search_idactivity)

        binding.FindID.setOnClickListener {
            val studentID = binding.studentID.text.toString()

            val intent = Intent(this, IDActivity::class.java)
            intent.putExtra("studentID",studentID)
            startActivity(intent)
        }

    }
}