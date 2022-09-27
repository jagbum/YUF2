package com.example.yuf2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.yuf2.databinding.ActivityJoinBinding
import com.example.yuf2.databinding.ActivityTermsBinding

class TermsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_terms)

        binding.temBtn.setOnClickListener {
            if(binding.term1check.isChecked && binding.term2check.isChecked){
                val intent = Intent(this, JoinActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "약관에 모두 동의해주세요.", Toast.LENGTH_SHORT).show()

            }
        }

    }
}