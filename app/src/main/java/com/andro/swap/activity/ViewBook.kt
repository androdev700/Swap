package com.andro.swap.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.andro.swap.R
import com.andro.swap.adapter.OwnerAdapter
import com.andro.swap.fragment.library.LibraryFragment
import com.andro.swap.model.BookItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_view_book.*


class ViewBook : AppCompatActivity() {

    private var mDatabase: DatabaseReference? = null
    private var onOffTrigger: Boolean = true
    private var ownerAdapter: OwnerAdapter? = null
    private var booksListener: ValueEventListener? = null
    private var booksList: ArrayList<BookItem>? = null
    private var ownerList: ArrayList<String>? = null

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

        ownerRecycler.layoutManager = LinearLayoutManager(this)
        ownerRecycler.setHasFixedSize(true)

        expand_book_data.setOnClickListener {
            expand_book_data.switchState()
            if (onOffTrigger) {
                book_data_extra.visibility = View.VISIBLE
                onOffTrigger = false
            } else {
                book_data_extra.visibility = View.GONE
                onOffTrigger = true
            }
        }

        val context: Context = this
        ownerList = ArrayList()

        mDatabase = FirebaseDatabase.getInstance().reference.child("books")
        booksListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("Owners", "Started")
                val children = dataSnapshot.children
                children.forEach {
                    val fetchedBook = it.getValue<BookItem>(BookItem::class.java)!!
                    if (fetchedBook.id == book.id) {
                        fetchedBook.owners?.forEach { _, u ->
                            var name = u.toString()
                            name = name.substring(name.indexOf("=") + 1, name.length - 1)
                            ownerList?.add(name)
                        }
                        ownerRecycler.adapter = OwnerAdapter(ownerList!!, context)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(LibraryFragment.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        mDatabase?.addListenerForSingleValueEvent(booksListener)
    }

    override fun onPause() {
        super.onPause()
        mDatabase?.removeEventListener(booksListener)
    }
}
