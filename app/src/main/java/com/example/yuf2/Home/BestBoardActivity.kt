package com.example.yuf2.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.databinding.DataBindingUtil
import com.example.yuf2.Board.ReadBoardActivity
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityBestBoardBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.bestPost
import com.example.yuf2.dataclass.post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class BestBoardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBestBoardBinding
    private val bestBoardData = mutableListOf<bestPost>()
    private val bestBoardKeyList = mutableListOf<String>()
    private lateinit var BestBoardAdpater: BestBoardAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best_board)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this, R.layout.activity_best_board)

        BestBoardAdpater = BestBoardAdapter(bestBoardData)

        binding.bestPost.adapter = BestBoardAdpater

        binding.bestPost.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, ReadBoardActivity::class.java)
            intent.putExtra("key", bestBoardKeyList[i])
            intent.putExtra("currentUID", auth.currentUser?.uid.toString())
            startActivity(intent)
        }

        getBestBoard()
    }

    fun getBestBoard(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                bestBoardData.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(bestPost::class.java)
                    bestBoardData.add(item!!)
                    bestBoardKeyList.add(dataModel.key.toString())

                }
                bestBoardKeyList.reverse()
                bestBoardData.reverse()
                BestBoardAdpater.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        Database.BestBoard.addValueEventListener(postListener)

    }


}