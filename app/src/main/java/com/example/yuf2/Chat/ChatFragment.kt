package com.example.yuf2.Chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.yuf2.R
import com.example.yuf2.databinding.FragmentBoardBinding
import com.example.yuf2.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private lateinit var binding : FragmentChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        ChatTool.chatFragmentContext = getContext()
        ChatTool
        binding.setting.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_settingFragment)
        }

        binding.notification.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_notiFragment)
        }

        binding.friend.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_friendFragment)
        }

        binding.board.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_boardFragment)
        }

        binding.home.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_homeFragment)
        }

        return binding.root
    }


}