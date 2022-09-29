package com.example.yuf2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.yuf2.databinding.ActivitySearchIdactivityBinding
import com.example.yuf2.databinding.ActivitySearchPwactivityBinding

class SearchPWActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchPwactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_pwactivity)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_search_pwactivity)

        binding.FindPW.setOnClickListener {
            val studentID = binding.studentID.text.toString()
            val name = binding.name.text.toString()
            val id = binding.email.text.toString()
            val intent = Intent(this, PWActivity::class.java)
            intent.putExtra("studentID",studentID)
            intent.putExtra("name",name)
            intent.putExtra("id",id)
            startActivity(intent)
        }

    }
}