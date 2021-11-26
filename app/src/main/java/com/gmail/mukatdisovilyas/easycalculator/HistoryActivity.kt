package com.gmail.mukatdisovilyas.easycalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.mukatdisovilyas.easycalculator.utils.Expression
import com.gmail.mukatdisovilyas.easycalculator.utils.HistoryAdapter
import com.gmail.mukatdisovilyas.easycalculator.utils.HistoryDatabaseHelper

class HistoryActivity : AppCompatActivity(), View.OnTouchListener
{
    private lateinit var llMenu: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAdapter
    private lateinit var tv: TextView
    private lateinit var img: ImageView
    private lateinit var btnMenu: ImageButton
    private lateinit var baseHelperHistory: HistoryDatabaseHelper
    private lateinit var list: MutableList<Expression>

    private lateinit var darkGrayColor: ColorStateList
    private lateinit var semiGrayColor: ColorStateList
    private lateinit var whiteColor: ColorStateList
    private lateinit var blackColor: ColorStateList


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        initViews()


        if (isDarkThemeOn())
        {
            btnMenu.backgroundTintList = blackColor
            btnMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_more_vert_white))
            tv.setTextColor(whiteColor)
            img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_history_white))
        }

        initPopUpMenu()

        baseHelperHistory = HistoryDatabaseHelper(this)
        list = baseHelperHistory.getAll()

        setVisibility()


        adapter = HistoryAdapter(list)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews()
    {
        recyclerView = findViewById(R.id.history_rv)
        tv = findViewById(R.id.tv_no_history)
        llMenu = findViewById(R.id.ll_menu)
        img = findViewById(R.id.iv_history)
        btnMenu = findViewById(R.id.btn_menu)
        btnMenu.setOnTouchListener(this)
        llMenu.setOnTouchListener(this)

        darkGrayColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        semiGrayColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.semi_gray))
        whiteColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        blackColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean
    {
        when (view.id)
        {
            R.id.btn_menu ->
            {
                when (motionEvent.action)
                {
                    MotionEvent.ACTION_DOWN ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList = darkGrayColor
                        else btnMenu.backgroundTintList = semiGrayColor
                    }
                    MotionEvent.ACTION_UP   ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList = blackColor
                        else btnMenu.backgroundTintList = whiteColor
                        view.performClick()
                    }
                }
            }

            R.id.ll_menu  ->
            {
                when (motionEvent.action)
                {
                    MotionEvent.ACTION_DOWN ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList = darkGrayColor
                        else btnMenu.backgroundTintList = semiGrayColor
                    }
                    MotionEvent.ACTION_UP   ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList = blackColor
                        else btnMenu.backgroundTintList = whiteColor
                        view.performClick()
                    }
                }
            }

        }
        return true
    }

    private fun setVisibility()
    {
        if (list.isEmpty())
        {
            tv.visibility = View.VISIBLE
            img.visibility = View.VISIBLE
            btnMenu.visibility = View.GONE
            llMenu.visibility = View.GONE
        }
    }


    @SuppressLint("NotifyDataSetChanged", "DiscouragedPrivateApi")
    private fun initPopUpMenu()
    {
        val popupMenu = PopupMenu(this, btnMenu)
        popupMenu.inflate(R.menu.menu_history)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId)
            {
                R.id.menu_delete ->
                {
                    AlertDialog.Builder(this).setTitle(getString(R.string.confirm_delete))
                        .setMessage(getString(R.string.delete_confirmation))
                        .setPositiveButton(getString(R.string.yes)) { _, _ ->
                            val helper = HistoryDatabaseHelper(this)
                            if (helper.clear())
                            {
                                Toast.makeText(this,
                                    getString(R.string.history_deleted),
                                    Toast.LENGTH_SHORT).show()
                                list.clear()
                                adapter.notifyDataSetChanged()
                                setVisibility()
                            }
                            else
                            {
                                Toast.makeText(this,
                                    getString(R.string.fail_deleting_history),
                                    Toast.LENGTH_SHORT).show()
                            }
                        }.setNegativeButton("No") { _, _ -> }.show()
                }
            }
            false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            popupMenu.setForceShowIcon(true)
        }
        else
        {
            try
            {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            }
            catch (e: Exception)
            {

            }
        }

        llMenu.setOnClickListener {
            popupMenu.show()
        }
        btnMenu.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun Context.isDarkThemeOn(): Boolean
    {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}