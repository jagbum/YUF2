package com.example.yuf2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.yuf2.databinding.ActivityJoinBinding
import com.example.yuf2.dataclass.Database
import com.example.yuf2.dataclass.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.regex.Pattern


class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding : ActivityJoinBinding

    private var check = false

    val regex_pw = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"
    val regex_name = "^[가-힣]*$"
    val regex_email = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
    val regex_phone = "^[0-9]{11}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this,R.layout.activity_join)

        binding.checkID.setOnClickListener {
            checkID()
        }

        binding.join.setOnClickListener{
            join()

        }

        getImg()
        binding.ID.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                check = false
            }

            override fun afterTextChanged(editable: Editable) {}
        })

    }

    fun join() {
        val name = binding.name.text.toString()
        val nickname = binding.nickname.text.toString()
        val studentID = binding.studentID.text.toString()
        val email = binding.ID.text.toString()
        val password = binding.PW.text.toString()
        val confirm = binding.PWCheck.text.toString()
        val phone = binding.phone.text.toString()
        var checkID = true

        if (name.equals("")) Toast.makeText(applicationContext, "이름을 입력하세요.", Toast.LENGTH_SHORT)
            .show()
        else if (!Pattern.matches(regex_name, name)) Toast.makeText(
            applicationContext,
            "이름을 확인하세요.",
            Toast.LENGTH_SHORT
        ).show()
        else if (password.equals("")) Toast.makeText(
            applicationContext,
            "비밀번호를 입력하세요.",
            Toast.LENGTH_SHORT
        ).show()
        else if (!Pattern.matches(regex_pw, password)) Toast.makeText(
            applicationContext,
            "비밀번호는 특수문자 포함 8~15자리입니다.",
            Toast.LENGTH_SHORT
        ).show()
        else if (phone.equals("")) Toast.makeText(
            applicationContext,
            "전화번호를 입력하세요.",
            Toast.LENGTH_SHORT
        ).show()
        else if (!Pattern.matches(regex_phone, phone)) Toast.makeText(
            applicationContext,
            "올바른 전화번호가 아닙니다.",
            Toast.LENGTH_SHORT
        ).show()
        else if (email.equals("")) Toast.makeText(
            applicationContext,
            "이메일을 입력하세요.",
            Toast.LENGTH_SHORT
        ).show()
        else if (!Pattern.matches(regex_email, email)) Toast.makeText(
            applicationContext,
            "올바른 이메일이 아닙니다.",
            Toast.LENGTH_SHORT
        ).show()
        else if (email.equals("")) Toast.makeText(
            applicationContext,
            "이메일을 입력하세요.",
            Toast.LENGTH_SHORT
        ).show()
        else{

            if (!password.equals(confirm)) {
                Toast.makeText(this, "비밀번호가 틀립니다. \n같은 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show()
                checkID = false
            }

            if (check) {
                if (checkID) {

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {

                                var uid = task.getResult().getUser()?.getUid().toString()
                                Log.i("tag", check.toString())

                                Database.user.child(studentID)
                                    .setValue(User(name, nickname, studentID, email, password))
                                Database.nickname.child(uid)
                                    .setValue(User(name, nickname, studentID, email, password))
                                saveImg(uid)
                                Toast.makeText(this, "회원가입을 완료했습니다!\n 로그인 해주세요!", Toast.LENGTH_LONG)
                                    .show()

                                val intent = Intent(this, LoginActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)


                            } else {
                                Log.i("tag", check.toString())

                                Toast.makeText(this, "다시 시도해주세요!", Toast.LENGTH_LONG).show()

                            }
                        }

                }
            } else {
                Toast.makeText(applicationContext, "사용할 수 없는 이메일입니다.", Toast.LENGTH_SHORT).show()

            }
        }

    }


    fun getImg(){

        val imageNum = (1..4).random()


        val basicImg = Firebase.storage.reference.child(imageNum.toString()+ ".jpg")

        val imageView = binding.image

        basicImg.downloadUrl.addOnCompleteListener(OnCompleteListener{ task->
            if(task.isSuccessful){
                Glide.with(this).load(task.result).into(imageView)
            }else{

            }
        })
    }
    fun checkID(){

        val email = binding.ID.text.toString()

        Database.nickname.orderByChild("id").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        Toast.makeText(applicationContext, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show()
                        check= true
                    } else {
                        Toast.makeText(applicationContext, "사용할 수 없는 이메일입니다.", Toast.LENGTH_SHORT).show()
                        binding.ID.setText("")

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
    }

    fun saveImg(key: String){

        val imgstorage = Firebase.storage

        val storageRef = imgstorage.reference
        val mountainsRef = storageRef.child(key+".jpg")

        val imageView = binding.image
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
        }.addOnSuccessListener { taskSnapshot ->

        }
    }



}


