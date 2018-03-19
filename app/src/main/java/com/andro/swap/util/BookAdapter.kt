package com.andro.swap.util

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andro.swap.R
import com.andro.swap.model.BookItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_book_item.view.*

class BookAdapter(private val books: ArrayList<BookItem>, private val context: Context) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(books[position])
    }

    override fun getItemCount() = books.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindForecast(book: BookItem) {
            itemView.book_name.text = book.volumeInfo?.title
            Glide.with(itemView).load(book.volumeInfo?.imageLinks?.thumbnail).into(itemView.book_cover)
        }
    }
}