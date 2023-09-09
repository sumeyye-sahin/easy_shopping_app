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

        auth= Firebase.auth // firebase auth nesnesi oluşturuldu


        val currentUser= auth.currentUser // eğer kullanıcı varsa currentUser değişkenine atar
        if(currentUser != null){ // eğer kullanıcı varsa
            val intent= Intent(this,FeedActivity::class.java) // intent oluşturuldu (bu aktiviteden FeedActivity'e geçiş yapmak için)
            startActivity(intent) // intent başlatıldı (FeedActivity'e geçiş yapmak için)
            finish() // geri dönüşü olmayan bir aktiviteye geçiş yapmak için kullanılır (bu aktiviteyi kapatır)
        }
    }

    fun signupClick (view: View){ // anlamı: signupClick, view ile tıklanır (görünüm ile tıklanır)

        val intent= Intent(this,SignUpActivity::class.java) // intent oluşturuldu (bu aktiviteden SignUpActivity'e geçiş yapmak için)
        startActivity(intent) // intent başlatıldı (SignUpActivity'e geçiş yapmak için)
       // finish() // geri dönüşü olmayan bir aktiviteye geçiş yapmak için kullanılır
    }

    fun singinClick (view: View){ // anlamı: singinClick, view ile tıklanır (görünüm ile tıklanır)
        val email= binding.emailText.text.toString() // e posta değişkeni oluşturuldu (emailText'ten alınır)
        val password= binding.passwordText.text.toString() // şifre değişkeni oluşturuldu (passwordText'ten alınır)

        if(email.equals("") || password.equals("")){ // eğer e posta veya şifre boş ise
            Toast.makeText(this,"Email and password cannot be empty!",Toast.LENGTH_LONG).show()
        } else { // eğer e posta veya şifre boş değil ise
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener { // e posta ve şifre ile giriş yapılır (başarılı olursa)
                val intent= Intent(this,SplashActivity2::class.java) // intent oluşturuldu (bu aktiviteden SplashActivity2'e geçiş yapmak için)
                startActivity(intent) // intent başlatıldı (SplashActivity2'e geçiş yapmak için)
                finish() // geri dönüşü olmayan bir aktiviteye geçiş yapmak için kullanılır (bu aktiviteyi kapatır)
            }.addOnFailureListener{ // e posta ve şifre ile giriş yapılır (başarısız olursa)
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show() // hata mesajı gösterilir
            }
        }
    }
}