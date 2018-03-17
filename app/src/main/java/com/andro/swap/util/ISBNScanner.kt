package com.andro.swap.util

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.andro.swap.R
import com.andro.swap.model.BooksApiResponse
import com.google.gson.Gson
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView


class ISBNScanner : AppCompatActivity(), ZBarScannerView.ResultHandler {

    companion object {
        private const val TAG = "ISBNScanner"
    }

    private lateinit var mScannerView: ZBarScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZBarScannerView(this)
        /*mScannerView.setFormats(listOf(BarcodeFormat.ISBN10, BarcodeFormat.ISBN13))*/
        mScannerView.setAutoFocus(true)
        setContentView(mScannerView)
        /*GetBook("9788129137654", this).execute()*/
    }

    public override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        Log.v(TAG, rawResult.contents)
        Log.v(TAG, rawResult.barcodeFormat.name)
        if (rawResult.barcodeFormat.name == "ISBN10" || rawResult.barcodeFormat.name == "ISBN13") {
            GetBook(rawResult.contents, this).execute()
        } else {
            Log.d(TAG, "Na ho payega!")
        }
    }

    inner class GetBook(private val isbn: String, val context: Context) : AsyncTask<Void, Void, String>() {

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
            return sh.makeServiceCall("https://www.googleapis.com/books/v1/volumes?q=${isbn}")
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressDialog.dismiss()
            val obj = Gson().fromJson(result, BooksApiResponse::class.java)

            val view = AlertDialog.Builder(context)
            val mLayout = View.inflate(context, R.layout.layout_book_picker, null)
            view.setTitle("We found these books...")
            view.setView(mLayout)
            val dialog = view.create()

            val bookList = mutableListOf<String>()
            obj.items?.forEach {
                bookList.add(it.volumeInfo?.title!!)
            }
            val listView = mLayout.findViewById<ListView>(R.id.bookSelectionList)
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, bookList)
            listView.adapter = adapter

            dialog.show()
        }
    }
}
