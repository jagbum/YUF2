package com.example.yuf2.Setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.yuf2.R
import com.example.yuf2.StartActivity
import com.example.yuf2.databinding.FragmentSettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class  SettingFragment : Fragment() {

    private lateinit var binding : FragmentSettingBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        binding.chatting.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_chatFragment)
        }

        binding.notification.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_notiFragment)
        }

        binding.friend.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_friendFragment)
        }

        binding.board.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_boardFragment)
        }

        binding.home.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_homeFragment)
        }

        binding.logout.setOnClickListener{
            auth.signOut()

            val intent = Intent(context, StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.myInfo.setOnClickListener {

            val intent = Intent(context, UserInfoActivity::class.java)
            intent.putExtra("currentUID", auth.currentUser?.uid.toString())
            startActivity(intent)
        }

        return binding.root
    }



}