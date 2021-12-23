package com.gmail.mukatdisovilyas.easycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.gmail.mukatdisovilyas.easycalculator.utils.EXTRA_URL

class WebViewActivity : AppCompatActivity()
{
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_EasyCalculator)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.webView)
        val intent = intent
        var url : String
        url=""
        if (intent != null)
        {
            url= intent.getStringExtra(EXTRA_URL)!!
        }
        webView.loadUrl(url)
    }
}