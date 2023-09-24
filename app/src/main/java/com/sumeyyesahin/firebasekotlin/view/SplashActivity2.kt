package com.sumeyyesahin.firebasekotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sumeyyesahin.firebasekotlin.databinding.ActivitySplash2Binding

class SplashActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivitySplash2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplash2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            splash.alpha = 0f // anlamı: splash alpha 0f (splash ekranı)
            splash.animate().setDuration(2000).alpha(1f).withEndAction { // anlamı: splash animasyonu 2000 milisaniye sürer ve alpha 1f olur (splash ekranı)
                val i = Intent(this@SplashActivity2, MainActivity::class.java) // anlamı: i, Intent ile oluşturuldu (splash ekranı)
                startActivity(i) // anlamı: i başlatılır (splash ekranı)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) // anlamı: geçiş animasyonu (splash ekranı)
                finish() // anlamı: splash ekranı kapatılır (splash ekranı)
            }
        }
    }
}
