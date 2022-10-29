package com.example.yuf2.Board

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.yuf2.OKActivity
import com.example.yuf2.R
import com.example.yuf2.databinding.FragmentBoardBinding
import com.example.yuf2.dataclass.post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BoardFragment : Fragment() {

    private lateinit var binding : FragmentBoardBinding
    private val boarddata = mutableListOf<post>()
    private val boardKeyList = mutableListOf<String>()
    private lateinit var BoardAdpater: BoardAdapter
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)

       /* BoardAdpater = BoardAdapter(boarddata)

        binding.post.adapter = BoardAdpater

        /binding.post.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(context, ReadBoardActivity::class.java)
            intent.putExtra("key", boardKeyList[i])
            startActivity(intent)
        }*/

        binding.setting.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_settingFragment)
        }

        binding.notification.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_notiFragment)
        }

        binding.friend.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_friendFragment)
        }

        binding.chatting.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_chatFragment)
        }

        binding.home.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
        }

        binding.write.setOnClickListener {
            WritePost()
        }

        return binding.root
    }

    fun WritePost(){
        val presentuid = auth.currentUser?.uid.toString()

        val intent = Intent(context, WriteBoardActivity::class.java)
        intent.putExtra("presentuid",presentuid)

        startActivity(intent)
    }


}