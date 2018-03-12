package com.andro.swap.util

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import me.dm7.barcodescanner.zbar.BarcodeFormat
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import android.os.AsyncTask
import android.app.ProgressDialog
import android.content.Context


class ISBNScannerHelper : AppCompatActivity(), ZBarScannerView.ResultHandler {

    companion object {
        private const val TAG = "ISBNScannerHelper"
    }
    private lateinit var mScannerView: ZBarScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZBarScannerView(this)
        setContentView(mScannerView)
        mScannerView.setFormats(listOf(BarcodeFormat.ISBN10, BarcodeFormat.ISBN13))
        mScannerView.setAutoFocus(true)
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
        GetBook(rawResult.contents, this).execute()
        finish()
    }

    inner class GetBook(val isbn: String, val context: Context) : AsyncTask<Void, Void, String>() {

        private val progressDialog: ProgressDialog = ProgressDialog(context);

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog.setMessage("Getting book details...");
            progressDialog.show();
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
        }

        override fun doInBackground(vararg params: Void?): String? {
            val sh = HttpHandler()
            val jsonStr = sh.makeServiceCall("https://www.googleapis.com/books/v1/volumes?q=" + isbn)

            return jsonStr
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d(TAG, result)
            progressDialog.dismiss()
        }
    }
}
