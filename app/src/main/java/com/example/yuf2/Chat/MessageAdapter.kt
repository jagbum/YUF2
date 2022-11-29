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


class MessageAdapter(var message_list: java.util.ArrayList<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.item_message, parent, false)
        return MessageHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val temp = message_list[position]

        holder.setItem(temp)
    }

    override fun getItemCount(): Int {
        return message_list.size
    }

    class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_message_nickname: TextView
        var tv_message_message: TextView
        var tv_message_update: TextView
        var ll_message: LinearLayout

        init {
            tv_message_nickname = itemView.findViewById<View>(R.id.tv_message_nickname) as TextView
            tv_message_message = itemView.findViewById<View>(R.id.tv_message_message) as TextView
            tv_message_update = itemView.findViewById<View>(R.id.tv_message_update) as TextView
            ll_message = itemView.findViewById<View>(R.id.ll_message) as LinearLayout
        }


        @SuppressLint("ResourceAsColor")
        fun setItem(item: Message) {
            tv_message_nickname.text = item.nickname
            tv_message_message.text = item.msg
            tv_message_update.text = item.update
            if (MessageActivity.mynickname.equals(item.nickname)) {
                ll_message.gravity = Gravity.RIGHT
            } else {
                ll_message.gravity = Gravity.LEFT

            }
        }
    }
}
