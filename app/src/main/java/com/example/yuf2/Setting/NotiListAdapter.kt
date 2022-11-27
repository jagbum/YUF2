package com.example.yuf2.Setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.yuf2.R
import com.example.yuf2.dataclass.Noti

class NotiListAdapter (val noti : MutableList<Noti>) : BaseAdapter() {

    override fun getCount(): Int {
        return noti.count()
    }

    override fun getItem(p0: Int): Any {
        return noti[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var view = p1

        if(view==null){
            view = LayoutInflater.from(p2?.context).inflate(R.layout.item_noti,p2,false)
        }

        val title = view?.findViewById<TextView>(R.id.title)
        title!!.text = noti[p0].title
        val content = view?.findViewById<TextView>(R.id.content)
        content!!.text = noti[p0].content

        return view!!
    }
}
