package com.andro.swap

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.andro.swap.home.HomeFragment
import com.andro.swap.library.LibraryFragment
import com.andro.swap.profile.ProfileFragment
import com.andro.swap.review.ReviewFragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val RC_SIGN_IN = 1
    private var mUsername: String? = null
    // Firebase instance variables
    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentTransaction: FragmentTransaction?
        when (item.itemId) {
            R.id.navigation_home -> {
                app_toolbar_text.text = getString(R.string.home_caps)
                fragmentTransaction = supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment.newInstance())
                fragmentTransaction?.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_library -> {
                app_toolbar_text.text = getString(R.string.library_caps)
                fragmentTransaction = supportFragmentManager.beginTransaction().replace(R.id.main_frame, LibraryFragment.newInstance())
                fragmentTransaction?.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_review -> {
                app_toolbar_text.text = getString(R.string.review_caps)
                fragmentTransaction = supportFragmentManager.beginTransaction().replace(R.id.main_frame, ReviewFragment.newInstance())
                fragmentTransaction?.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                app_toolbar_text.text = getString(R.string.profile_caps)
                fragmentTransaction = supportFragmentManager.beginTransaction().replace(R.id.main_frame, ProfileFragment.newInstance())
                fragmentTransaction?.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BottomNavigationViewHelper.disableShiftMode(navigation)
        navigation.selectedItemId = R.id.home
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

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
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        //inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*when (item.getItemId()) {
            R.id.sign_out_menu -> {
                AuthUI.getInstance().signOut(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }*/
        return true
    }

    private fun onSignedInInitialize(username: String?) {
        mUsername = username
    }

    private fun onSignedOutCleanup() {

    }

}
