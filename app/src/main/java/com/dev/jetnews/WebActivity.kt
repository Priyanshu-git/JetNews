package com.dev.jetnews

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebView
import android.widget.Toast

class WebActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_layout)
        val webview: WebView = findViewById(R.id.web_frame)
        webview.settings.javaScriptEnabled = true

        val url = intent.getStringExtra("url")
        if (url != null) {
            webview.loadUrl(url)
        } else {
            Toast.makeText(this, "URL not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}