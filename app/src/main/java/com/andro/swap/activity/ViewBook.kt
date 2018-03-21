package com.andro.swap.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.andro.swap.R
import com.andro.swap.model.BookItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_view_book.*


class ViewBook : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_book)

        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val book = intent.getSerializableExtra("bookData") as BookItem

        app_toolbar_text.text = book.volumeInfo?.title

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_book_placeholder)
        requestOptions.error(R.drawable.ic_book_placeholder)

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(book.volumeInfo?.imageLinks?.thumbnail)
                .into(bookCover)

        bookTitle.text = book.volumeInfo?.title
        bookAuthor.text = book.volumeInfo?.authors?.toString()
        bookDesc.text = book.volumeInfo?.description
        bookLang.text = "Language: ${book.volumeInfo?.language}"
        bookPageCount.text = "Pages: ${book.volumeInfo?.pageCount}"
        bookCategory.text = book.volumeInfo?.categories?.toString()
        bookPublisher.text = book.volumeInfo?.publisher
        bookPublishedDate.text = book.volumeInfo?.publishedDate
    }
}
