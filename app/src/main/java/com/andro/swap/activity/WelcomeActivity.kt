package com.andro.swap.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.andro.swap.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class WelcomeActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 1
    }

    // Firebase instance variables
    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseAuth = FirebaseAuth.getInstance()

        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                onSignedInInitialize(user)
            } else {
                onSignedOutCleanup()
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(true)
                        .setAvailableProviders(Arrays.asList(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build()))
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

    private fun onSignedInInitialize(user: FirebaseUser) {
        getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("userName", user.displayName).apply()
        getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("email", user.email).apply()
        getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("uId", user.uid).apply()
        getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("photoUrl", user.photoUrl.toString()).apply()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun onSignedOutCleanup() {

    }
}
