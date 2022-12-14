package com.example.yuf2.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.yuf2.R
import com.example.yuf2.dataclass.bestPost

class recentBestBoardAdapter (val board : MutableList<bestPost>) : BaseAdapter() {
    override fun getCount(): Int {
        if(board.size<=5) {
            return board.size
        }else{
            return 5
        }
    }

    override fun getItem(p0: Int): Any {
        return board[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var view = p1
        if(view == null){
            view = LayoutInflater.from(p2?.context).inflate(R.layout.recentposts_item, p2, false)
        }

        val title = view?.findViewById<TextView>(R.id.title)
        title!!.text = board[p0].title

        view?.setOnTouchListener(View.OnTouchListener { v, event -> true })

        return view!!
    }
}