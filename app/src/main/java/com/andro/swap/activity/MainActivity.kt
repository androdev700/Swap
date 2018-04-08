package com.andro.swap.activity

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
import com.andro.swap.R
import com.andro.swap.fragment.home.HomeFragment
import com.andro.swap.fragment.library.LibraryFragment
import com.andro.swap.fragment.profile.ProfileFragment
import com.andro.swap.fragment.review.ReviewFragment
import com.andro.swap.util.BottomNavigationViewHelper
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 1
    }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            R.id.navigation_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BottomNavigationViewHelper.disableShiftMode(navigation)
        setSupportActionBar(findViewById(R.id.app_toolbar))
        supportActionBar?.title = ""

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseAuth = FirebaseAuth.getInstance()

        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                onSignedOutCleanup()
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(true)
                        .setAvailableProviders(Arrays.asList(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setTheme(R.style.AppTheme)
                        .build(), RC_SIGN_IN)
            } else {
                onSignedInInitialize(user)
            }
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home


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
    }

    private fun onSignedOutCleanup() {

    }
}
