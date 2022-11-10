package com.example.yuf2.Board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.yuf2.R
import com.example.yuf2.dataclass.post

class BoardAdapter(val boardlist : MutableList<post>) : BaseAdapter() {
    override fun getCount(): Int {
        return boardlist.size
    }

    override fun getItem(p0: Int): Any {
        return boardlist[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var view = p1
        if(view == null){
            view = LayoutInflater.from(p2?.context).inflate(R.layout.boardlist, p2, false)
        }

        val title = view?.findViewById<TextView>(R.id.title)
        title!!.text = boardlist[p0].title

        val content = view?.findViewById<TextView>(R.id.content)
        content!!.text = boardlist[p0].content

        val like = view?.findViewById<TextView>(R.id.like)
        like!!.text = boardlist[p0].starCount.toString()

        return view!!
    }
}