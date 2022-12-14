package com.example.yuf2.Chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yuf2.Chat.ChatAdapter.ChatHolder
import com.example.yuf2.R
import com.example.yuf2.dataclass.Chat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.ArrayList

class ChatAdapter(val list: java.util.ArrayList<Chat>?) : RecyclerView.Adapter<ChatHolder>() {
    private var auth: FirebaseAuth = Firebase.auth
    var chat_list: ArrayList<Chat>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_chat, parent, false)
        return ChatHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val temp = chat_list!![position]
        holder.setItem(temp)
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context.applicationContext, MessageActivity::class.java)
            intent.putExtra(
                "otherid",
                if (auth.currentUser?.uid.toString().equals(temp.frontid)) temp.endid else temp.frontid
            )

            intent.putExtra(
                "othernickname",
                if (auth.currentUser?.uid.toString().equals(temp.frontid)) temp.endnickname else temp.frontnickname
            )

            intent.putExtra(
                "mynickname",
                if (auth.currentUser?.uid.toString().equals(temp.frontid)) temp.frontnickname else temp.endnickname
            )

            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chat_list!!.size
    }

    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var auth: FirebaseAuth = Firebase.auth
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

            if (auth.currentUser?.uid.toString().equals(item.frontid)){

                val profileImg = Firebase.storage.reference.child(item.endid+ ".jpg")

                profileImg.downloadUrl.addOnCompleteListener(OnCompleteListener{ task->
                    if(task.isSuccessful){
                        Glide.with(itemView!!).load(task.result).into(iv_chat_image!!)
                    }else{

                    }
                })

            } else{

                val profileImg = Firebase.storage.reference.child(item.frontid+ ".jpg")

                profileImg.downloadUrl.addOnCompleteListener(OnCompleteListener{ task->
                    if(task.isSuccessful){
                        Glide.with(itemView!!).load(task.result).into(iv_chat_image!!)
                    }else{

                    }
                })
            }

            tv_chat_name.text = if (auth.currentUser?.uid.toString().equals(item.frontid)) item.endnickname else item.frontnickname
            tv_chat_last.text = item.last
            tv_chat_update.setText(ChatTool.getChangetime(item.update))
        }
    }
}