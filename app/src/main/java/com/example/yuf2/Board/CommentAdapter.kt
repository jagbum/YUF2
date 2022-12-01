package com.example.yuf2.Board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.yuf2.R
import com.example.yuf2.dataclass.comment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CommentAdapter (val comments: MutableList<comment>) : BaseAdapter() {
    private lateinit var auth: FirebaseAuth

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
        auth = Firebase.auth

        var view = p1
        if(view == null){
            view = LayoutInflater.from(p2?.context).inflate(R.layout.comment_item, p2, false)
        }

        val nickname = view?.findViewById<TextView>(R.id.CommentUserNickname)
        nickname!!.text = comments[p0].nickname

        val comment = view?.findViewById<TextView>(R.id.comment)
        comment!!.text = comments[p0].comment

        val delete = view?.findViewById<ImageView>(R.id.delete)
        val report = view?.findViewById<ImageView>(R.id.report)


        if(comments[p0].uid.equals(auth.currentUser?.uid.toString())){
            delete!!.isVisible= true
        }else{
            report!!.isVisible= true
        }

        return view!!
    }
}
