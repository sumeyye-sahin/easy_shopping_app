package com.sumeyyesahin.firebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sumeyyesahin.firebasekotlin.MainActivity
import com.sumeyyesahin.firebasekotlin.databinding.ActivitySplash2Binding


class SplashActivity2 : AppCompatActivity() {


    private lateinit var binding: ActivitySplash2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplash2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            splash.alpha = 0f
            splash.animate().setDuration(2000).alpha(1f).withEndAction {
                val i = Intent(this@SplashActivity2, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
    }
}