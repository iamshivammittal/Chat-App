package com.example.mukeshmittal.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
      val auth by lazy{
          FirebaseAuth.getInstance()
      }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth.addAuthStateListener {
            if(it.currentUser!= null){
                startActivity(Intent(this,ChatActivity::class.java))
            }
        }
        Loginbtn.setOnClickListener {
            auth.createUserWithEmailAndPassword(email.text.toString(),pass.text.toString())
                .addOnSuccessListener {
                    Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                    val profileUpdate= UserProfileChangeRequest.Builder()
                        .setDisplayName(username.text.toString())
                        .build()
                    it.user?.updateProfile(profileUpdate)
                }.addOnFailureListener {
                    if(it.localizedMessage.contains("Already",true)){
                        signIn(email.text.toString(),pass.text.toString())
                    }else{
                        Toast.makeText(this,"${it.localizedMessage}",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun signIn(email:String,pass: String){
        auth.signInWithEmailAndPassword(email,pass)
            .addOnSuccessListener {
                Toast.makeText(this,"Logged In Successfully",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this,"${it.localizedMessage}",Toast.LENGTH_LONG).show()
            }
    }
}
