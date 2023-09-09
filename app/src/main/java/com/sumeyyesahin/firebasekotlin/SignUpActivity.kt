package com.sumeyyesahin.firebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sumeyyesahin.firebasekotlin.databinding.ActivitySignupBinding


class SignUpActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //tooolbarda geri gitmek için
        supportActionBar!!.title=""

        auth= Firebase.auth


    }
    fun denemeClicked (view: View) {

        val email = binding.emailText.text.toString()  // e posta değişkeni oluşturuldu (emailText'ten alınır)
        val password = binding.passwordText.text.toString() // şifre değişkeni oluşturuldu (passwordText'ten alınır)

        if (email.equals("") || password.equals("")) { // eğer e posta veya şifre boş ise


            Toast.makeText(this, "Email and password cannot be empty!", Toast.LENGTH_LONG).show() // hata mesajı gösterilir
        }
        else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                // e posta ve şifre ile giriş yapılır (başarılı olursa)

                val intent = Intent(this,MainActivity::class.java) // intent oluşturuldu (bu aktiviteden MainActivity'e geçiş yapmak için)
                startActivity(intent) // intent başlatıldı (MainActivity'e geçiş yapmak için)
                finish() // bu aktivite kapatıldı (bu aktivite) (geri dönüşte bu aktiviteye gelinmez)
            }.addOnFailureListener { // e posta ve şifre ile giriş yapılır (başarısız olursa)
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show() // hata mesajı gösterilir
            }
        }
    }}