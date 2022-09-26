package com.example.yuf2.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.yuf2.R
import com.example.yuf2.databinding.FragmentBoardBinding
import com.example.yuf2.databinding.FragmentNotiBinding


class NotiFragment : Fragment() {

    private lateinit var binding : FragmentNotiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_noti, container, false)

        binding.setting.setOnClickListener {
            it.findNavController().navigate(R.id.action_notiFragment_to_settingFragment)
        }

        binding.friend.setOnClickListener {
            it.findNavController().navigate(R.id.action_notiFragment_to_friendFragment)
        }

        binding.chatting.setOnClickListener {
            it.findNavController().navigate(R.id.action_notiFragment_to_chatFragment)
        }

        binding.board.setOnClickListener {
            it.findNavController().navigate(R.id.action_notiFragment_to_boardFragment)
        }

        binding.home.setOnClickListener {
            it.findNavController().navigate(R.id.action_notiFragment_to_homeFragment)
        }

        return binding.root
    }


}