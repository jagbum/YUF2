package com.example.yuf2.Board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.yuf2.R
import com.example.yuf2.dataclass.comment

class CommentAdapter (val comments: MutableList<comment>) : BaseAdapter() {

    override fun getCount(): Int {
        return comments.size
    }

    override fun getItem(p0: Int): Any {
        return comments[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var view = p1
        if(view == null){
            view = LayoutInflater.from(p2?.context).inflate(R.layout.comment_item, p2, false)
        }

        val nickname = view?.findViewById<TextView>(R.id.CommentUserNickname)
        nickname!!.text = comments[p0].nickname

        val comment = view?.findViewById<TextView>(R.id.comment)
        comment!!.text = comments[p0].comment

        return view!!
    }
}
