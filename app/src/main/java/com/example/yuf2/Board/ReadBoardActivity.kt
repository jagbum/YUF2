package com.example.yuf2.Board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityReadBoardBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.User
import com.example.yuf2.dataclass.comment
import com.example.yuf2.dataclass.post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ReadBoardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var DB: FirebaseDatabase
    private lateinit var binding : ActivityReadBoardBinding

    private lateinit var key :String

    private val commentList = mutableListOf<comment>()
    private lateinit var uid :String
    private lateinit var currentNickname :String
    private var likepoint :Int=0

    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_board)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_read_board)

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

                // Set value and report transaction success
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

                    binding.nickname.text = item!!.nickname
                    binding.Title.text = item!!.title
                    binding.Content.text = item!!.content
                    binding.like.text = item!!.starCount.toString()
                    auth = FirebaseAuth.getInstance()

                    val presentuid = auth.currentUser?.uid.toString()
                    val datauid = item.uid

                }catch (e: Exception){

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.Board.child(key).addValueEventListener(postListener)

    }

    fun saveComment(){
        auth = FirebaseAuth.getInstance()
        val nickname = currentNickname
        val comment = binding.commentInput.text.toString()
        val uid = auth.currentUser?.uid.toString()

        Database.comment.child(key).push().setValue(comment(nickname, comment, uid))
        binding.commentInput.setText("")
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