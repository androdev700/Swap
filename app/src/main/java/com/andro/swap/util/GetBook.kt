package com.andro.swap.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.andro.swap.R
import com.andro.swap.model.ApiResponse
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson

class GetBook(private val isbn: String, private val context: Context, private val activity: Activity)
    : AsyncTask<Void, Void, String>() {

    private val progressDialog: ProgressDialog = ProgressDialog(context)

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog.setMessage("Getting book details...")
        progressDialog.show()
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
    }

    override fun doInBackground(vararg params: Void?): String? {
        val sh = HttpHandler()
        return sh.makeServiceCall("https://www.googleapis.com/books/v1/volumes?q=$isbn")
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        progressDialog.dismiss()
        val apiResponse = Gson().fromJson(result, ApiResponse::class.java)

        val view = AlertDialog.Builder(context)
        val mLayout = View.inflate(context, R.layout.layout_book_picker, null)
        view.setTitle("We found these books...")
        view.setView(mLayout)
        val dialog = view.create()

        val bookList = mutableListOf<String>()
        apiResponse.items?.forEach {
            bookList.add(it.volumeInfo?.title!!)
        }
        val listView = mLayout.findViewById<ListView>(R.id.bookSelectionList)
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, bookList)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, id ->
            val mDatabase = FirebaseDatabase.getInstance().reference
            mDatabase.child("books").child(apiResponse.items!![position].id).setValue(apiResponse.items[position])
            val uId = context.getSharedPreferences("userData", Context.MODE_PRIVATE).getString("uId", "")
            if (uId != "") {
                mDatabase.child("userdata").child(uId).child("bookCollection").child(apiResponse.items[position].id).setValue(apiResponse.items[position])
            }
            Toast.makeText(context, "${bookList[position]} added to library.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            activity.finish()
        }

        if (listView.adapter.isEmpty) {
            Toast.makeText(context, "No books found for the provided ISBN", Toast.LENGTH_SHORT).show()
        } else {
            dialog.show()
        }
    }
}