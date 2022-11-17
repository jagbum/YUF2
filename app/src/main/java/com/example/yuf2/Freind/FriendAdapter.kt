package com.example.yuf2.Freind

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.yuf2.R
import com.example.yuf2.dataclass.Friend


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

        return view!!
    }
}