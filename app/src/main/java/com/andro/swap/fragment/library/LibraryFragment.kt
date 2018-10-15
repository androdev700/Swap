package com.andro.swap.fragment.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andro.swap.R
import com.andro.swap.activity.AddBookActivity
import com.andro.swap.adapter.BookAdapter
import com.andro.swap.model.BookItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_library.*

class LibraryFragment : androidx.fragment.app.Fragment() {

    companion object {
        const val TAG = "LibraryFragment"
        fun newInstance(): LibraryFragment {
            return LibraryFragment()
        }
    }

    private var mDatabase: DatabaseReference? = null
    private var booksListener: ValueEventListener? = null
    private var booksList: ArrayList<BookItem>? = null
    private var booksAdapter: BookAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uId = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)!!.getString("uId", "")
        mDatabase = FirebaseDatabase.getInstance().reference.child("userdata").child(uId).child("bookCollection")

        if (library_recycler != null) {
            library_recycler.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
            library_recycler.setHasFixedSize(true)
        }
        val context = context

        booksList = java.util.ArrayList()
        booksAdapter = BookAdapter(booksList!!, context!!, activity!!)

        booksListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get BookItem object and use the values to update the UI
                val children = dataSnapshot.children
                children.forEach {
                    booksList?.add(it.getValue<BookItem>(BookItem::class.java)!!)
                }

                if (progress_container != null)
                    progress_container.visibility = View.GONE
                if (library_recycler != null) {
                    library_recycler.adapter = booksAdapter
                    booksAdapter?.notifyDataSetChanged()
                }

                if (booksList?.size == 0) {
                    Toast.makeText(context, "You have no books in your library.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting BookItem failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        mDatabase?.addListenerForSingleValueEvent(booksListener)

        lib_fab.setOnClickListener {
            startActivity(Intent(activity, AddBookActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        mDatabase?.removeEventListener(booksListener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }
}
