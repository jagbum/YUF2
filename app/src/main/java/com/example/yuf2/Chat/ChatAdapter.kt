package com.example.yuf2.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yuf2.Chat.ChatAdapter.ChatHolder
import com.example.yuf2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatAdapter : RecyclerView.Adapter<ChatHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_chat, parent, false)
        return ChatHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val temp = ChatTool.chat_list[position]
        holder.setItem(temp)
    }

    override fun getItemCount(): Int {
        return ChatTool.chat_list.size
    }

    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var auth : FirebaseAuth = Firebase.auth
        var iv_chat_image: ImageView
        var iv_chat_new: ImageView
        var tv_chat_name: TextView
        var tv_chat_last: TextView
        var tv_chat_update: TextView

        init {
            iv_chat_image = itemView.findViewById<View>(R.id.iv_chat_image) as ImageView
            iv_chat_new = itemView.findViewById<View>(R.id.iv_chat_new) as ImageView
            tv_chat_name = itemView.findViewById<View>(R.id.tv_chat_name) as TextView
            tv_chat_last = itemView.findViewById<View>(R.id.tv_chat_last) as TextView
            tv_chat_update = itemView.findViewById<View>(R.id.tv_chat_update) as TextView
        }

        fun setItem(item: Chat) {
            tv_chat_name.text = if (auth.currentUser?.uid.toString().equals(item.frontid)) item.endnickname else item.frontnickname
            tv_chat_last.text = item.last
            tv_chat_update.setText(ChatTool.getChangetime(item.update))
        }
    }
}