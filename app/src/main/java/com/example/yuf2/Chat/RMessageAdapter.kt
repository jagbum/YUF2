package com.example.yuf2.Chat

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yuf2.R


class RMessageAdapter(var message_list: java.util.ArrayList<Message>) :
    RecyclerView.Adapter<RMessageAdapter.RMessageHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RMessageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.item_rmessage, parent, false)
        return RMessageHolder(itemView)
    }


    override fun onBindViewHolder(holder: RMessageHolder, position: Int) {
        val temp = message_list[position]
        if (RMessageActivity.mynickname.equals(temp.nickname)) {
            holder.back.setBackgroundColor(Color.rgb(251,244,141))
        } else {
            holder.back.setBackgroundColor(Color.rgb(222,226,230))

        }
        holder.setItem(temp)
    }

    override fun getItemCount(): Int {
        return message_list.size
    }

    class RMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_rmessage_nickname: TextView
        var tv_rmessage_message: TextView
        var tv_rmessage_update: TextView
        var ll_rmessage: LinearLayout
        var back: LinearLayout

        init {
            tv_rmessage_nickname = itemView.findViewById<View>(R.id.tv_rmessage_nickname) as TextView
            tv_rmessage_message = itemView.findViewById<View>(R.id.tv_rmessage_message) as TextView
            tv_rmessage_update = itemView.findViewById<View>(R.id.tv_rmessage_update) as TextView
            ll_rmessage = itemView.findViewById<View>(R.id.ll_rmessage) as LinearLayout
            back = itemView.findViewById<View>(R.id.back) as LinearLayout

        }


        @SuppressLint("ResourceAsColor")
        fun setItem(item: Message) {
            tv_rmessage_nickname.text = item.nickname
            tv_rmessage_message.text = item.msg
            tv_rmessage_update.text = item.update
            if (RMessageActivity.mynickname.equals(item.nickname)) {
                ll_rmessage.gravity = Gravity.RIGHT
            } else {
                ll_rmessage.gravity = Gravity.LEFT
            }
        }
    }
}
