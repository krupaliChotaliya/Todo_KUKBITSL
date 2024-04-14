package com.android.todo_kukbitsl

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)

        webView.webViewClient = MyWebViewClient()

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true   // Enable JavaScript (only if needed)d)
        webSettings.domStorageEnabled = true   // Enable DOM Storage (if needed)
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW // Enforce HTTPS
        webSettings.allowFileAccess = false // Prevent file access
        webView.webChromeClient = WebChromeClient()

        webView.loadUrl("https://kuk-todolist.netlify.app/")
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            Log.i("onReceivedError",error.toString())
            Toast.makeText(this@MainActivity, "Failed to load the page", Toast.LENGTH_SHORT).show()
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            super.onReceivedSslError(view, handler, error)
            Log.i("onReceivedSslError",error.toString())
            Toast.makeText(this@MainActivity, "SSL Error: Failed to load the page", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}