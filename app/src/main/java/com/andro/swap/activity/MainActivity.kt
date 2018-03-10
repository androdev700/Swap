package com.andro.swap.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.andro.swap.R
import com.andro.swap.fragment.home.HomeFragment
import com.andro.swap.fragment.library.LibraryFragment
import com.andro.swap.fragment.profile.ProfileFragment
import com.andro.swap.fragment.review.ReviewFragment
import com.andro.swap.util.BottomNavigationViewHelper
import com.andro.swap.util.ISBNScannerHelper
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

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
                startActivity(Intent(this, ISBNScannerHelper::class.java))
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

        setSupportActionBar(findViewById(R.id.app_toolbar))
        supportActionBar?.title = ""

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addBook -> {
                startActivity(Intent(this, AddBookActivity::class.java))
            }
            R.id.logout -> {
                AuthUI.getInstance().signOut(this)
            }
        }
        super.onOptionsItemSelected(item)
        return true
    }
}
