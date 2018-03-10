package com.andro.swap.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.andro.swap.R
import com.andro.swap.util.ISBNScannerHelper
import kotlinx.android.synthetic.main.activity_add_book.*

class AddBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        scanISBN.setOnClickListener {
            startActivity(Intent(it.context, ISBNScannerHelper::class.java))
        }
    }
}
