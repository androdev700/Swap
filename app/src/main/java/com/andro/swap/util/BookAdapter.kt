package com.andro.swap.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andro.swap.R
import com.andro.swap.activity.ViewBook
import com.andro.swap.model.BookItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.layout_book_item.view.*

class BookAdapter(private val books: ArrayList<BookItem>, private val context: Context, private val activity: FragmentActivity)
    : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(books[position], context, activity)
    }

    override fun getItemCount() = books.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindForecast(book: BookItem, context: Context, activity: Activity) {
            itemView.book_name.text = book.volumeInfo?.title

            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.ic_book_placeholder)
            requestOptions.error(R.drawable.ic_book_placeholder)

            Glide.with(itemView)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(book.volumeInfo?.imageLinks?.thumbnail)
                    .into(itemView.book_cover)

            itemView.setOnClickListener {
                val intent = Intent(activity, ViewBook::class.java)
                intent.putExtra("bookData", book)
                context.startActivity(intent)
            }
        }
    }
}