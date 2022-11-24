package com.example.yuf2.Noti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.yuf2.R
import com.example.yuf2.dataclass.FriendNoti

class FriendNotiAdapter (val friend : MutableList<FriendNoti>) : BaseAdapter() {
    override fun getCount(): Int {
        return friend.size

    }

    override fun getItem(p0: Int): Any {
        return friend[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var view = p1
        if(view == null){
            view = LayoutInflater.from(p2?.context).inflate(R.layout.friendnoti_item, p2, false)
        }

        val title = view?.findViewById<TextView>(R.id.title)
        title!!.text = friend[p0].name

        return view!!
    }
}