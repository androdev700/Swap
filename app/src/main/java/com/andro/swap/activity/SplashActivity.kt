package com.andro.swap.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.andro.swap.R


class SplashActivity : AppCompatActivity() {

    private val TIMEOUT = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val i: Intent
            if (getSharedPreferences("isFirstLaunch", Context.MODE_PRIVATE).getBoolean("IS_FIRST", true)) {
                i = Intent(this@SplashActivity, IntroActivity::class.java)
            } else {
                i = Intent(this@SplashActivity, MainActivity::class.java)
            }
            startActivity(i)
            finish()
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        }, TIMEOUT)
    }
}
