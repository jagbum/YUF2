package com.example.yuf2.Board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityReadBoardBinding
import com.example.yuf2.dataclass.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ReadBoardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityReadBoardBinding

    private lateinit var key :String

    private val commentList = mutableListOf<comment>()
    private lateinit var uid :String
    private lateinit var currentNickname :String

    private lateinit var postTitle :String
    private lateinit var content :String
    private lateinit var postNickname :String
    private lateinit var postUID :String

    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_board)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_read_board)
        auth = Firebase.auth

        uid = intent.getStringExtra("currentUID").toString()

        commentAdapter = CommentAdapter(commentList)
        binding.comment.adapter = commentAdapter


        key = intent.getStringExtra("key").toString()

        binding.like.setOnClickListener {
            onStarClicked(Database.Board.child(key))
        }

        binding.commentSave.setOnClickListener{
            saveComment()
            var keyboard: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            keyboard.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }

        binding.follow.setOnClickListener {
            saveFriend()
        }

        binding.delete.setOnClickListener {
            removePost()
        }

        binding.edit.setOnClickListener {
            editPost()
        }

        getnick(uid)
        getData(key)
        getComment(key)
    }

    private fun onStarClicked(postRef: DatabaseReference) {
        // ...
        postRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val p = mutableData.getValue(post::class.java)
                    ?: return Transaction.success(mutableData)

                if (p.stars.containsKey(uid)) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1
                    p.stars.remove(uid)
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1
                    p.stars[uid] = true
                }

                if(p.starCount>=5){
                    Database.BestBoard.child(key).setValue(bestPost(postTitle,content,postNickname,p.starCount,key))
                }
                else{
                    Database.BestBoard.child(key).removeValue()
                }

                mutableData.value = p

                return Transaction.success(mutableData)
            }

            override fun onComplete(
                databaseError: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
            }
        })
    }

    fun getnick(uid: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val item = dataSnapshot.getValue(User::class.java)

                     currentNickname = item!!.nickname

                }catch (e: Exception){

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.nickname.child(uid).addValueEventListener(postListener)
    }

    fun getData(key:String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val item = dataSnapshot.getValue(post::class.java)

                    postNickname = item!!.nickname
                    postTitle = item!!.title
                    content = item!!.content
                    postUID = item!!.uid

                    binding.nickname.text = item!!.nickname
                    binding.Title.text = item!!.title
                    binding.Content.text = item!!.content
                    binding.likePoint.text = item!!.starCount.toString()
                    auth = FirebaseAuth.getInstance()

                    val presentuid = auth.currentUser?.uid.toString()

                    if(postUID.equals(presentuid)){
                        binding.delete.isVisible = true
                        binding.edit.isVisible = true
                    }else{
                        binding.follow.isVisible = true
                        binding.report.isVisible = true
                    }

                }catch (e: Exception){

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.Board.child(key).addValueEventListener(postListener)
    }

    fun removePost(){
        Database.Board.child(key).removeValue()
        Database.BestBoard.child(key).removeValue()
        Database.comment.child(key).removeValue()
        Database.nickname.child(uid).child("MyBoard").child(key).removeValue()

        finish()
    }

    fun editPost(){
        val intent = Intent(this, EditPostActivity::class.java)
        intent.putExtra("key",key)
        startActivity(intent)
    }

    fun saveComment(){
        auth = FirebaseAuth.getInstance()
        val nickname = currentNickname
        val comment = binding.commentInput.text.toString()
        val uid = auth.currentUser?.uid.toString()

        Database.comment.child(key).push().setValue(comment(nickname, comment, uid))

        if(auth.currentUser?.uid.toString().equals(postUID)){}
        else {
            Database.nickname.child(postUID).child("notification").child("commentnoti").push()
                .setValue(CommentNoti(postTitle, key))
        }

        binding.commentInput.setText("")
    }

    fun saveFriend(){

        Database.nickname.child(uid).child("Friend").orderByChild("uid").equalTo(postUID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        Database.nickname.child(uid).child("Friend").child(postUID).setValue(Friend(postNickname, postUID))
                        Database.nickname.child(postUID).child("notification").child("friendnoti").child(uid).setValue(FriendNoti(currentNickname,uid))
                        Toast.makeText(applicationContext, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(applicationContext, "????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show()                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
    }

    fun getComment(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                commentList.clear()

                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(comment::class.java)
                    commentList.add(item!!)
                }

                commentAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        Database.comment.child(key).addValueEventListener(postListener)
    }
}