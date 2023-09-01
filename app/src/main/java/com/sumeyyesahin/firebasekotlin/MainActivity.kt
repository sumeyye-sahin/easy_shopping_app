package com.sumeyyesahin.firebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sumeyyesahin.firebasekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {   // onCreate aktivite ilk oluşturulduğunda çalışır
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)

        auth= Firebase.auth


        val currentUser= auth.currentUser // eğer kullanıcı varsa currentUser değişkenine atar
        if(currentUser != null){
            val intent= Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signupClick (view: View){

        val intent= Intent(this,SignUpActivity::class.java)
        startActivity(intent)
       // finish()

    }

    fun singinClick (view: View){


        val email= binding.emailText.text.toString()
        val password= binding.passwordText.text.toString()

        if(email.equals("") || password.equals("")){
            Toast.makeText(this,"Email and password cannot be empty!",Toast.LENGTH_LONG).show()
        } else {
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent= Intent(this,SplashActivity2::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }


}