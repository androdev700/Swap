package com.andro.swap.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andro.swap.R
import com.andro.swap.model.Owner
import kotlinx.android.synthetic.main.layout_book_owner.view.*

class OwnerAdapter(private val owners: ArrayList<Owner>, private val context: Context)
    : RecyclerView.Adapter<OwnerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_book_owner, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return owners.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(owners[position], context)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(owner: Owner, context: Context) {
            itemView.book_owner_label.text = owner.name
            if (owner.rating != 0f) {
                itemView.book_owner_rating.text = String.format("%.1f/5.0", owner.rating)
            } else {
                itemView.book_owner_rating.text = "Not Rated"
            }
        }
    }
}