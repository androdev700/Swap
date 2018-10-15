package com.andro.swap.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.andro.swap.R
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment

class IntroActivity : AppIntro2() {

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFadeAnimation()

        addSlide(AppIntro2Fragment
                .newInstance("Simple", "Swap is simple to use.",
                        R.drawable.ic_simple_onboard, resources.getColor(R.color.primaryColor)))
        addSlide(AppIntro2Fragment
                .newInstance("Library", "Add your physical book collection for the world to see.",
                        R.drawable.ic_book_onboard, resources.getColor(R.color.primaryColor)))
        addSlide(AppIntro2Fragment
                .newInstance("Sell/Exchange", "Meet people of your interest.",
                        R.drawable.ic_sell_onboard, resources.getColor(R.color.primaryColor)))
        addSlide(AppIntro2Fragment
                .newInstance("Review", "Write book reviews for the world to see.",
                        R.drawable.ic_rate_onboard, resources.getColor(R.color.primaryColor)))

        showSkipButton(true)
        isProgressButtonEnabled = true
    }

    override fun onSkipPressed(currentFragment: androidx.fragment.app.Fragment) {
        super.onSkipPressed(currentFragment)
        finish()
        startActivity(Intent(this@IntroActivity, WelcomeActivity::class.java))
    }

    override fun onDonePressed(currentFragment: androidx.fragment.app.Fragment) {
        super.onDonePressed(currentFragment)
        finish()
        startActivity(Intent(this@IntroActivity, WelcomeActivity::class.java))
    }
}