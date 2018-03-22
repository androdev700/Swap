package com.andro.swap.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.andro.swap.R
import com.andro.swap.util.GetBook
import com.andro.swap.util.ISBNScanner
import kotlinx.android.synthetic.main.activity_add_book.*

class AddBookActivity : AppCompatActivity() {

    private lateinit var bookFetchThread: GetBook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        setSupportActionBar(app_toolbar_add_book)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        bookFetchThread = GetBook(editTextISBN.text.toString(), this, this)

        addBookButton.setOnClickListener {
            if (editTextISBN.text.length < 10) {
                Toast.makeText(this, "ISBN too short", Toast.LENGTH_LONG).show()
            } else {
                bookFetchThread = GetBook(editTextISBN.text.toString(), this, this)
                bookFetchThread.execute()
            }
        }

        scanISBN.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
                }
            } else {
                startActivity(Intent(it.context, ISBNScanner::class.java))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        bookFetchThread.cancel(true)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startActivity(Intent(this, ISBNScanner::class.java))
                } else {
                    Toast.makeText(this, "Permission Denied. Enable to use ISBN Scanner", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}
