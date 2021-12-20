package com.allana.bfaa.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allana.bfaa.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val imgLogoGithub = imgLogoGithub
        val tvLinkGithub = tvLinkGithub

        imgLogoGithub.alpha = 0f
        tvLinkGithub.alpha = 0f
        tvLinkGithub.animate().setDuration(2000).alpha(1f)
        imgLogoGithub.animate().setDuration(2000).alpha(1f).withEndAction {
            val moveToMain = Intent(this, MainActivity::class.java)
            startActivity(moveToMain)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}