package com.example.yuf2.Noti

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.yuf2.Board.ReadBoardActivity
import com.example.yuf2.Home.recentBestBoardAdapter
import com.example.yuf2.Home.recentBoardAdapter
import com.example.yuf2.R
import com.example.yuf2.databinding.FragmentHomeBinding
import com.example.yuf2.databinding.FragmentNotiBinding
import com.example.yuf2.dataclass.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class NotiFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : FragmentNotiBinding
    private val friendNoti = mutableListOf<FriendNoti>()
    private val commentNoti = mutableListOf<CommentNoti>()
    private val friendNotiKeyList = mutableListOf<String>()
    private val commentNotiKeyList = mutableListOf<String>()
    private lateinit var friendNotiAdpater: FriendNotiAdapter
    private lateinit var commentNotiAdpater: CommentNotiAdapter
    private lateinit var friendName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_noti, container, false)


        friendNotiAdpater = FriendNotiAdapter(friendNoti)
        commentNotiAdpater = CommentNotiAdapter(commentNoti)

        binding.friendNoti.adapter = friendNotiAdpater
        binding.commentNoti.adapter = commentNotiAdpater

        binding.friendNoti.setOnItemClickListener{ adapterView, view, i, l ->
            saveFriend(friendNotiKeyList[i])
        }

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


        getCommentNotification()
        getFriendNotification()

        return binding.root
    }

    fun getFriendNotification(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                friendNoti.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(FriendNoti::class.java)
                    friendNoti.add(item!!)
                    friendNotiKeyList.add(dataModel.key.toString())

                }
                friendNotiKeyList.reverse()
                friendNoti.reverse()
                friendNotiAdpater.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }


        Database.nickname.child(auth.currentUser?.uid.toString()).child("notification").child("friendnoti").addValueEventListener(postListener)

    }


    fun getCommentNotification(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                commentNoti.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(CommentNoti::class.java)
                    commentNoti.add(item!!)
                    commentNotiKeyList.add(dataModel.key.toString())

                }
                commentNotiKeyList.reverse()
                commentNoti.reverse()
                commentNotiAdpater.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        Database.nickname.child(auth.currentUser?.uid.toString()).child("notification").child("commentnoti").addValueEventListener(postListener)

    }

    fun saveFriend(uid:String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val item = dataSnapshot.getValue(FriendNoti::class.java)

                    friendName = item!!.name


                }catch (e: Exception){

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.nickname.child(auth.currentUser?.uid.toString()).child("notification").child("friendnoti").child(uid).addValueEventListener(postListener)

        Database.nickname.child(auth.currentUser?.uid.toString()).child("Friend").orderByChild("uid").equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        Database.nickname.child(auth.currentUser?.uid.toString()).child("Friend").child(uid).setValue(Friend(friendName, uid))
                        Toast.makeText(context, "친구에 추가하였습니다.", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(context, "친구에 추가된 사람입니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
    }


}