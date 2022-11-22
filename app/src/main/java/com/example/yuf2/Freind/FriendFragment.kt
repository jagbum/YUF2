package com.example.yuf2.Freind

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
import com.example.yuf2.R
import com.example.yuf2.databinding.FragmentBoardBinding
import com.example.yuf2.databinding.FragmentFriendBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.Friend
import com.example.yuf2.dataclass.User
import com.example.yuf2.dataclass.post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class FriendFragment : Fragment() {

    private lateinit var binding : FragmentFriendBinding
    private val frienddata = mutableListOf<Friend>()
    private val friendKeyList = mutableListOf<String>()
    private lateinit var FriendAdpater: FriendAdapter
    private lateinit var auth: FirebaseAuth

    private lateinit var myName :String
    private lateinit var myProfile :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend, container, false)

        FriendAdpater = FriendAdapter(frienddata)

        binding.myfriend.adapter = FriendAdpater

        binding.myfriend.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("key", friendKeyList[i])
            intent.putExtra("currentUID", auth.currentUser?.uid.toString())
            startActivity(intent)
        }

        binding.setting.setOnClickListener {
            it.findNavController().navigate(R.id.action_friendFragment_to_settingFragment)
        }

        binding.notification.setOnClickListener {
            it.findNavController().navigate(R.id.action_friendFragment_to_notiFragment)
        }

        binding.chatting.setOnClickListener {
            it.findNavController().navigate(R.id.action_friendFragment_to_chatFragment)
        }

        binding.board.setOnClickListener {
            it.findNavController().navigate(R.id.action_friendFragment_to_boardFragment)
        }

        binding.home.setOnClickListener {
            it.findNavController().navigate(R.id.action_friendFragment_to_homeFragment)
        }

        getMyProfile()
        getFriend()

        return binding.root
    }

    fun getMyProfile(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try{
                    val item = dataSnapshot.getValue(User::class.java)

                    myName = item!!.nickname
                    myProfile = item!!.state

                    binding.myName.text = item!!.nickname
                    binding.myState.text = item!!.state

                }catch (e: Exception){

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        Database.nickname.child(auth.currentUser?.uid.toString()).addValueEventListener(postListener)
    }

    fun getFriend(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                frienddata.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(Friend::class.java)
                    frienddata.add(item!!)
                    friendKeyList.add(dataModel.key.toString())

                }
                friendKeyList.reverse()
                frienddata.reverse()
                FriendAdpater.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        Database.nickname.child(auth.currentUser?.uid.toString()).child("Friend").addValueEventListener(postListener)

    }

}