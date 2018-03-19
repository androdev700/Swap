package com.andro.swap.fragment.library

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andro.swap.R
import com.andro.swap.model.BookItem
import com.andro.swap.util.BookAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_library.*

class LibraryFragment : Fragment() {

    companion object {
        const val TAG = "LibraryFragment"
        fun newInstance(): LibraryFragment {
            return LibraryFragment()
        }
    }

    private lateinit var mDatabase: DatabaseReference
    private lateinit var booksListener: ValueEventListener
    private lateinit var booksList: ArrayList<BookItem>
    private lateinit var booksAdapter: BookAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uId = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)!!.getString("uId", "")
        mDatabase = FirebaseDatabase.getInstance().reference.child("userdata").child(uId).child("bookCollection")

        val gridLayoutManager = GridLayoutManager(context, 2)
        library_recycler.layoutManager = gridLayoutManager
        library_recycler.setHasFixedSize(true)
        val context = context

        booksListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get BookItem object and use the values to update the UI
                val children = dataSnapshot.children
                booksList = java.util.ArrayList()
                children.forEach {
                    booksList.add(it.getValue<BookItem>(BookItem::class.java)!!)
                }

                progress_container.visibility = View.GONE
                booksAdapter = BookAdapter(booksList, context!!)
                library_recycler.adapter = booksAdapter
                booksAdapter.notifyDataSetChanged()

                if (booksList.size == 0) {
                    Toast.makeText(context, "You have no books in your library.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting BookItem failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        mDatabase.addListenerForSingleValueEvent(booksListener)
    }

    override fun onStop() {
        super.onStop()
        mDatabase.removeEventListener(booksListener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }
}
