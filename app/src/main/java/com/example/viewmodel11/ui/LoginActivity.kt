package com.example.viewmodel11.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.viewmodel11.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenSplash = installSplashScreen()

        setContentView(R.layout.activity_login)

        Thread.sleep(1000)
        screenSplash.setKeepOnScreenCondition{false}

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}