package com.andro.swap.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import com.andro.swap.R
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment

class IntroActivity : AppIntro2() {

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFadeAnimation()

        addSlide(AppIntro2Fragment.newInstance("Simple", "Swap is simple to use", R.drawable.ic_home_black_24dp, resources.getColor(R.color.primaryColor)))
        addSlide(AppIntro2Fragment.newInstance("Welcome", ">SWAP DESCRIPTION<", R.drawable.ic_home_black_24dp, resources.getColor(R.color.primaryColor)))
        addSlide(AppIntro2Fragment.newInstance("Welcome", ">SWAP DESCRIPTION<", R.drawable.ic_home_black_24dp, resources.getColor(R.color.primaryColor)))
        addSlide(AppIntro2Fragment.newInstance("Welcome", ">SWAP DESCRIPTION<", R.drawable.ic_home_black_24dp, resources.getColor(R.color.primaryColor)))

        showSkipButton(true)
        isProgressButtonEnabled = true
    }

    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        finish()
        startActivity(Intent(this@IntroActivity, WelcomeActivity::class.java))
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        finish()
        startActivity(Intent(this@IntroActivity, WelcomeActivity::class.java))
    }
}