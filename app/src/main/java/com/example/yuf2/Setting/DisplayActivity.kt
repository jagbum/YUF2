package com.example.yuf2.Setting

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.yuf2.R

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        findViewById<RadioGroup>(R.id.radioMode).setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.rbLight -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.rbDark -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
//                R.id.rbDefault -> {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        //APP SDK 버전이 Q보다 클 때 (API 29 이상)
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//                    } else {
//                        //APP SDK 버전이 Q보다 작을 때 (API 29 미만)
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
//                    }
//                }
            }
        }



    }
}