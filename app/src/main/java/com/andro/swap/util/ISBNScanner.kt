package com.andro.swap.util

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
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
        mScannerView.setAutoFocus(true)
        setContentView(mScannerView)
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
            Toast.makeText(this, "Unable to process barcode, Try Again!", Toast.LENGTH_LONG).show()
        }
    }
}
