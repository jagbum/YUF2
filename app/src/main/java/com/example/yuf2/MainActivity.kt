package com.example.yuf2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private var i=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed(){
        if(i==2){
            ActivityCompat.finishAffinity(this)
        }
        i += 1

        Toast.makeText(this,"종료하시려면 더블클릭해주세요", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable{ i=0 },2000)
    }
}