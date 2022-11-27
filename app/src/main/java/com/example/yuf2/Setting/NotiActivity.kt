package com.example.yuf2.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yuf2.R
import com.example.yuf2.dataclass.Noti
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class NotiActivity : AppCompatActivity() {
    lateinit var  NLAdapter : NotiListAdapter

    val list  = mutableListOf<Noti>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti)

//        val writeBtn = findViewById<Button>(R.id.writeBtn)
//        writeBtn.setOnClickListener {
//
//            val intent = Intent(this,BoardWriteActivity::class.java)
//            startActivity(intent)
//
//
//        }


        NLAdapter = NotiListAdapter(list)

        val nt = findViewById<ListView>(R.id.noti)
        nt.adapter = NLAdapter

        getData()
    }

    fun getData(){

        val database = Firebase.database
        val myRef = database.getReference("noti")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                // ...
                for(dataModel in dataSnapshot.children){

                    val item = dataModel.getValue(Noti::class.java)
//                    Log.d("BoardListActivity",item.toString())
                    list.add(item!! )
                }
                //동기화 처리
                NLAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        myRef.addValueEventListener(postListener)

    }
}

