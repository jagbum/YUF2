package com.example.yuf2.Setting

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.databinding.DataBindingUtil
import com.example.yuf2.R
import com.example.yuf2.databinding.ActivityUserInfoBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.Database.Companion.nickname
import com.example.yuf2.dataclass.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.lang.Exception

class UserInfoActivity : AppCompatActivity() {

    var database = Firebase.database

    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var key: String
    private lateinit var uid: String



//    private lateinit var nickname :String
//    private lateinit var state :String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        uid = intent.getStringExtra("currentUID").toString()
        key = intent.getStringExtra("key").toString()


        binding.save.setOnClickListener {
            edit()
        }

        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }

        getData()

    }

    fun getData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val item = dataSnapshot.getValue(User::class.java)

//                    nickname = item!!.nickname
                    //  state = item!!.state
//                    binding.name.setText(item!!.nickname)
                    binding.state.setText(item!!.state)


                } catch (e: Exception) {

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        Database.nickname.child(uid).addValueEventListener(postListener)
    }

    fun edit() {

        Database.nickname.child(uid).child("state").setValue(binding.state.text.toString())

        imageUpload()

        finish()

    }

    private fun imageUpload(){
        // Get the data from an ImageView as bytes

        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(uid + ".jpg")


        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==100){
            binding.imageArea.setImageURI(data?.data)
        }
    }


}
