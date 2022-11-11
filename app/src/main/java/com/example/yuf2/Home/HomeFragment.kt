package com.example.yuf2.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.yuf2.Board.BoardAdapter
import com.example.yuf2.Board.ReadBoardActivity
import com.example.yuf2.Board.WriteBoardActivity
import com.example.yuf2.LoginActivity
import com.example.yuf2.R
import com.example.yuf2.databinding.FragmentHomeBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.bestPost
import com.example.yuf2.dataclass.post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding : FragmentHomeBinding

    private val recentBoard = mutableListOf<post>()
    private val bestBoard = mutableListOf<bestPost>()
    private val recentboardKeyList = mutableListOf<String>()
    private val bestboardKeyList = mutableListOf<String>()
    private lateinit var recentBoardAdpater: recentBoardAdapter
    private lateinit var bestBoardAdpater: recentBestBoardAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        recentBoardAdpater = recentBoardAdapter(recentBoard)
        bestBoardAdpater = recentBestBoardAdapter(bestBoard)

        binding.recentBoard.adapter = recentBoardAdpater
        binding.bestBoard.adapter = bestBoardAdpater

        binding.bestBoardArea.setOnClickListener {
            val intent = Intent(context, BestBoardActivity::class.java)
            startActivity(intent)
        }
        binding.BoardArea.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
        }

        binding.setting.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }

        binding.notification.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_notiFragment)
        }

        binding.friend.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_friendFragment)
        }

        binding.chatting.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_chatFragment)
        }

        binding.board.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
        }

        getBoard()
        getBestBoard()

        return binding.root
    }

    fun getBoard(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                recentBoard.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(post::class.java)
                    recentBoard.add(item!!)
                    recentboardKeyList.add(dataModel.key.toString())

                }
                recentboardKeyList.reverse()
                recentBoard.reverse()
                recentBoardAdpater.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        Database.Board.addValueEventListener(postListener)

    }

    fun getBestBoard(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                bestBoard.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(bestPost::class.java)
                    bestBoard.add(item!!)
                    bestboardKeyList.add(dataModel.key.toString())

                }
                bestboardKeyList.reverse()
                bestBoard.reverse()
                bestBoardAdpater.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        Database.Board.addValueEventListener(postListener)

    }


}