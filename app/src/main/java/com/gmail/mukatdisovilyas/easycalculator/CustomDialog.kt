package com.gmail.mukatdisovilyas.easycalculator


import android.os.Bundle

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.gmail.mukatdisovilyas.easycalculator.utils.EXTRA_URL


class CustomDialog    // TODO Auto-generated constructor stub
    (private val c: Activity) : Dialog(c), View.OnClickListener
{
    private lateinit var d: Dialog
    private lateinit var yes: Button
    private lateinit var no: Button
    private lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_custom)
        yes = findViewById(R.id.btn_contact)
        no = findViewById(R.id.btn_dismiss)
        text = findViewById(R.id.dialog_text)
        val aboutText = c.getString(R.string.about_text) + BuildConfig.VERSION_NAME
        text.text = aboutText
        yes.setOnClickListener(this)
        no.setOnClickListener(this)
    }

    override fun onClick(v: View)
    {
        when (v.id)
        {
            R.id.btn_contact ->
            {
                val intent2 = Intent(c, WebViewActivity()::class.java
                )
                intent2.putExtra(EXTRA_URL, c.getString(R.string.my_telegram))
                startActivity(c, intent2, null)
            }
            R.id.btn_dismiss -> dismiss()
            else             ->
            {
            }
        }
        dismiss()
    }
}