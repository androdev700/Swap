package com.andro.swap.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.widget.Toast
import com.andro.swap.R
import com.firebase.ui.auth.AuthUI
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class IntroActivity : AppIntro2() {

    // Firebase instance variables
    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null

    private val RC_SIGN_IN = 1

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFadeAnimation()

        addSlide(AppIntro2Fragment.newInstance("Welcome", ">SWAP DESCRIPTION<", R.drawable.ic_home_black_24dp, resources.getColor(R.color.primaryColor)))
        addSlide(AppIntro2Fragment.newInstance("Welcome", ">SWAP DESCRIPTION<", R.drawable.ic_home_black_24dp, resources.getColor(R.color.primaryColor)))
        addSlide(AppIntro2Fragment.newInstance("Welcome", ">SWAP DESCRIPTION<", R.drawable.ic_home_black_24dp, resources.getColor(R.color.primaryColor)))
        addSlide(AppIntro2Fragment.newInstance("Welcome", ">SWAP DESCRIPTION<", R.drawable.ic_home_black_24dp, resources.getColor(R.color.primaryColor)))


        showSkipButton(true)
        isProgressButtonEnabled = true

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseAuth = FirebaseAuth.getInstance()

        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
                onSignedInInitialize(user.displayName)
            } else {
                // User is signed out
                onSignedOutCleanup()
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(true)
                        .setAvailableProviders(Arrays.asList(
                                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .setTheme(R.style.AppTheme)
                        .build(), RC_SIGN_IN)
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show()
                getSharedPreferences("isFirstLaunch", Context.MODE_PRIVATE).edit().putBoolean("IS_FIRST", false).apply()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mFirebaseAuth!!.addAuthStateListener(mAuthStateListener!!)
    }

    override fun onPause() {
        super.onPause()
        if (mAuthStateListener != null) {
            mFirebaseAuth!!.removeAuthStateListener(mAuthStateListener!!)
        }
    }

    private fun onSignedInInitialize(username: String?) {

    }

    private fun onSignedOutCleanup() {

    }

    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        finish()
        startActivity(Intent(this@IntroActivity, MainActivity::class.java))
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        finish()
        startActivity(Intent(this@IntroActivity, MainActivity::class.java))
    }
}