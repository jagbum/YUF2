package com.example.yuf2.Freind

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.yuf2.R
import com.example.yuf2.databinding.FragmentFriendBinding
import com.example.yuf2.dataclass.Friend
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FriendAdapter (val myFriend : MutableList<Friend>) : BaseAdapter() {

    override fun getCount(): Int {
        return myFriend.size
    }

    override fun getItem(p0: Int): Any {
        return myFriend[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var view = p1
        if(view == null){
            view = LayoutInflater.from(p2?.context).inflate(R.layout.friend_item, p2, false)
        }

        val nickname = view?.findViewById<TextView>(R.id.nickname)
        nickname!!.text = myFriend[p0].nickname

        val profileImg = Firebase.storage.reference.child(myFriend[p0].uid+ ".jpg")

        val imageView = view?.findViewById<ImageView>(R.id.image)

        profileImg.downloadUrl.addOnCompleteListener(OnCompleteListener{ task->
            if(task.isSuccessful){
                Glide.with(view!!).load(task.result).into(imageView!!)
            }else{

            }
        })


        return view!!
    }
}


