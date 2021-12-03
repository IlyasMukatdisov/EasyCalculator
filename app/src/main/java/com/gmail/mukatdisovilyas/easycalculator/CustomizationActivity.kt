package com.gmail.mukatdisovilyas.easycalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.azeesoft.lib.colorpicker.ColorPickerDialog
import com.gmail.mukatdisovilyas.easycalculator.utils.*


class CustomizationActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener
{

    private lateinit var llMenu: LinearLayout

    private lateinit var btnNumbersColor: Button
    private lateinit var btnActionsColor: Button
    private lateinit var btnAcColor: Button
    private lateinit var btnEqualColor: Button
    private lateinit var btnTextColor: Button

    private lateinit var btnSave: Button
    private lateinit var btnMenu: ImageButton


    private lateinit var btnRounded: Button
    private lateinit var btnRectangle: Button
    private lateinit var btnCircle: Button


    private lateinit var radioRounded: RadioButton
    private lateinit var radioRectangle: RadioButton
    private lateinit var radioCircle: RadioButton


    private lateinit var radioGroup: RadioGroup

    private lateinit var tvTitle: TextView
    private lateinit var tvNumbers: TextView
    private lateinit var tvActions: TextView
    private lateinit var tvAc: TextView
    private lateinit var tvEqual: TextView
    private lateinit var tvTextColor: TextView


    private var numbersColor: String = ""
    private var numberPicked = false

    private var actionsColor: String = ""
    private var actionPicked = false

    private var acColor: String = ""
    private var acPicked = false

    private var equalColor: String = ""
    private var equalPicked = false

    private var textColor: String = ""
    private var textColorPicked = false

    private var shape: String = ""
    private var shapePicked = false


    private var textSize = 0f


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customization)

        initViews()

        if (isDarkThemeOn())
        {
            setDefaultDarkThemeColors()

        }

        if (intent.hasExtra(MAIN_TEXT_SIZE))
        {
            textSize = intent.getFloatExtra(MAIN_TEXT_SIZE, 0f) - 8f

            setTextSizes(textSize)


        }

        customization()

        initPopUpMenu()


    }

    private fun setDefaultDarkThemeColors()
    {
        btnMenu.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
        btnMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_more_vert_white))
        btnNumbersColor.setTextColor(ContextCompat.getColor(this, R.color.white))
        btnRounded.setTextColor(ContextCompat.getColor(this, R.color.white))
        btnRectangle.setTextColor(ContextCompat.getColor(this, R.color.white))
        btnCircle.setTextColor(ContextCompat.getColor(this, R.color.white))
        tvTitle.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        tvNumbers.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        tvActions.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        tvAc.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        tvEqual.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        tvTextColor.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
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
                        if (isDarkThemeOn()) btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                        else btnMenu.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                    }
                    MotionEvent.ACTION_UP   ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
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
                        if (isDarkThemeOn()) btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                        else btnMenu.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                    }
                    MotionEvent.ACTION_UP   ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                        view.performClick()
                    }
                }
            }

        }
        return true
    }


    private fun setTextSizes(textSize: Float)
    {
        btnNumbersColor.textSize = textSize
        btnActionsColor.textSize = textSize
        btnAcColor.textSize = textSize
        btnEqualColor.textSize = textSize
        btnSave.textSize = textSize

        btnTextColor.textSize = textSize

        val tvTextSize = textSize * 3 / 2


        tvNumbers.textSize = tvTextSize
        tvActions.textSize = tvTextSize
        tvAc.textSize = tvTextSize
        tvEqual.textSize = tvTextSize
        tvTextColor.textSize = tvTextSize
        tvTitle.textSize = tvTextSize

        btnRounded.textSize = textSize
        btnRectangle.textSize = textSize
        btnCircle.textSize = textSize

    }

    private fun customization()
    {
        if (!CustomModelDatabaseHelper(this).isCustomizationEmpty())
        {
            val list = CustomModelDatabaseHelper(this).getAll()
            val numbersColor = list[0].numberButtonsColor
            val actionsColor = list[0].actionButtonsColor
            val acColor = list[0].acButtonColor
            val equalColor = list[0].equalButtonColor
            val textColor = list[0].textColor

            setButtonsShape(list[0].shape)


            if (numbersColor.isNotEmpty())
            {
                btnNumbersColor.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor(numbersColor))

                btnActionsColor.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor(actionsColor))
                btnAcColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(acColor))
                btnEqualColor.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor(equalColor))

                btnRounded.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor(numbersColor))
                btnRectangle.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor(numbersColor))
                btnCircle.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor(numbersColor))
                btnTextColor.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor(textColor))
            }

        }
        else
        {
            if (this.isDarkThemeOn())
            {
                btnNumbersColor.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                btnRounded.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                btnRectangle.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                btnCircle.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                btnTextColor.backgroundTintList =
                    AppCompatResources.getColorStateList(this, R.color.white)

            }
            else
            {
                btnNumbersColor.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
                btnRectangle.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
                btnRounded.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
                btnCircle.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
                btnTextColor.backgroundTintList =
                    AppCompatResources.getColorStateList(this, R.color.black)
            }
            btnActionsColor.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_blue)
            btnAcColor.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_green)
            btnEqualColor.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.magenta)

            setButtonsShape(SHAPE_ROUNDED)

        }
    }

    private fun setButtonsShape(shape: String)
    {
        when (shape)
        {
            SHAPE_ROUNDED   ->
            {
                btnNumbersColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_round)
                btnActionsColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_round)
                btnAcColor.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
                btnEqualColor.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
                btnTextColor.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
            }
            SHAPE_RECTANGLE ->
            {
                btnNumbersColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
                btnActionsColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
                btnAcColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
                btnEqualColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
                btnTextColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
            }
            SHAPE_CIRCLE    ->
            {
                btnNumbersColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_circle)
                btnActionsColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_circle)
                btnAcColor.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
                btnEqualColor.background =
                    ContextCompat.getDrawable(this, R.drawable.buttons_circle)
                btnTextColor.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
            }
        }
    }

    private fun Context.isDarkThemeOn(): Boolean
    {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews()
    {

        llMenu = findViewById(R.id.ll_menu)


        btnNumbersColor = findViewById(R.id.btn_numbers_color)
        btnActionsColor = findViewById(R.id.btn_actions_color)
        btnAcColor = findViewById(R.id.btn_ac_color)
        btnEqualColor = findViewById(R.id.btn_equal_color)
        btnSave = findViewById(R.id.btn_cc_save)
        btnMenu = findViewById(R.id.btn_menu)
        btnTextColor = findViewById(R.id.btn_text_color)

        btnRounded = findViewById(R.id.btn_rounded)
        btnRectangle = findViewById(R.id.btn_rectangle)
        btnCircle = findViewById(R.id.btn_circle)

        tvTitle = findViewById(R.id.tv_cc_title)
        tvNumbers = findViewById(R.id.tv_cc_numbers_color)
        tvActions = findViewById(R.id.tv_cc_actions_color)
        tvAc = findViewById(R.id.tv_cc_ac_color)
        tvEqual = findViewById(R.id.tv_cc_equal_color)
        radioGroup = findViewById(R.id.radio_group_shapes)
        tvTextColor = findViewById(R.id.tv_buttons_text_color)
        radioRounded = findViewById(R.id.radio_rounded)
        radioRectangle = findViewById(R.id.radio_rectangle)
        radioCircle = findViewById(R.id.radio_circle)


        btnNumbersColor.setOnClickListener(this)
        btnActionsColor.setOnClickListener(this)
        btnAcColor.setOnClickListener(this)
        btnEqualColor.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnMenu.setOnClickListener(this)
        btnTextColor.setOnClickListener(this)
        btnRounded.setOnClickListener(this)
        btnRectangle.setOnClickListener(this)
        btnCircle.setOnClickListener(this)



        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId)
            {
                R.id.radio_rounded   ->
                {
                    shape = SHAPE_ROUNDED
                    setButtonsShape(shape)
                }
                R.id.radio_rectangle ->
                {
                    shape = SHAPE_RECTANGLE
                    setButtonsShape(shape)
                }
                R.id.radio_circle    ->
                {
                    shape = SHAPE_CIRCLE
                    setButtonsShape(shape)
                }
            }
            shapePicked = true
            shouldEnableBtnSave()
        }

        btnMenu.setOnTouchListener(this)
        llMenu.setOnTouchListener(this)

    }

    override fun onClick(v: View?)
    {

        val colorPickerDialog: ColorPickerDialog? = if (this.isDarkThemeOn())
        {
            ColorPickerDialog.createColorPickerDialog(this, ColorPickerDialog.DARK_THEME)
        }
        else
        {
            ColorPickerDialog.createColorPickerDialog(this, ColorPickerDialog.LIGHT_THEME)
        }
        when (v?.id)
        {
            R.id.btn_numbers_color ->
            {
                colorPickerDialog?.setOnColorPickedListener { _, hexVal ->
                    btnNumbersColor.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(hexVal))
                    btnRounded.backgroundTintList = ColorStateList.valueOf(Color.parseColor(hexVal))
                    btnRectangle.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(hexVal))
                    btnCircle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(hexVal))
                    numbersColor = hexVal
                    numberPicked = true
                    shouldEnableBtnSave()

                }
                colorPickerDialog?.show()
            }
            R.id.btn_actions_color ->
            {
                colorPickerDialog?.setOnColorPickedListener { _, hexVal ->
                    btnActionsColor.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(hexVal))
                    actionsColor = hexVal
                    actionPicked = true
                    shouldEnableBtnSave()
                }
                colorPickerDialog?.show()
            }
            R.id.btn_ac_color      ->
            {
                colorPickerDialog?.setOnColorPickedListener { _, hexVal ->
                    btnAcColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(hexVal))
                    acColor = hexVal
                    acPicked = true
                    shouldEnableBtnSave()
                }
                colorPickerDialog?.show()
            }
            R.id.btn_equal_color   ->
            {
                colorPickerDialog?.setOnColorPickedListener { _, hexVal ->
                    btnEqualColor.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(hexVal))
                    equalColor = hexVal
                    equalPicked = true
                    shouldEnableBtnSave()
                }
                colorPickerDialog?.show()
            }

            R.id.btn_text_color    ->
            {
                colorPickerDialog?.setOnColorPickedListener { _, hexVal ->
                    btnTextColor.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(hexVal))
                    textColor = hexVal
                    textColorPicked = true
                    setButtonsTextColor()
                    shouldEnableBtnSave()

                }
                colorPickerDialog?.show()
            }

            R.id.btn_cc_save       ->
            {
                val intent = Intent(this, MainActivity::class.java)
                val customColorHelper = CustomModelDatabaseHelper(this)
                customColorHelper.addOne(numbersColor, actionsColor, acColor, equalColor, textColor,
                    shape)
                startActivity(intent)
            }

            R.id.btn_rounded       ->
            {
                shape = SHAPE_ROUNDED
                shapePicked = true
                setButtonsShape(shape)
                radioRounded.isChecked = true
            }

            R.id.btn_rectangle     ->
            {
                shape = SHAPE_RECTANGLE
                shapePicked = true
                setButtonsShape(shape)
                radioRectangle.isChecked = true
            }

            R.id.btn_circle        ->
            {
                shape = SHAPE_CIRCLE
                shapePicked = true
                setButtonsShape(shape)
                radioCircle.isChecked = true
            }

        }
    }

    private fun setButtonsTextColor()
    {
        btnNumbersColor.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnActionsColor.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnAcColor.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnEqualColor.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnRounded.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnRectangle.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnCircle.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
    }


    @SuppressLint("DiscouragedPrivateApi")
    private fun initPopUpMenu()
    {
        val popupMenu = PopupMenu(this, btnMenu)
        popupMenu.inflate(R.menu.menu_color)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId)
            {
                R.id.menu_restore_colors ->
                {
                    AlertDialog.Builder(this).setTitle("Confirm delete")
                        .setMessage("Are you sure want to restore custom colors?")
                        .setPositiveButton("Yes") { _, _ ->
                            val helper = CustomModelDatabaseHelper(this)
                            if (helper.clear())
                            {
                                customization()
                                Toast.makeText(this, "Colors successfully restored!",
                                    Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                Toast.makeText(this,
                                    "Something went wrong. Please try again later!",
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


    private fun isAllFieldFilled(): Boolean
    {
        return numberPicked && actionPicked && acPicked && equalPicked && textColorPicked && shapePicked
    }

    private fun shouldEnableBtnSave()
    {
        if (isAllFieldFilled())
        {
            btnSave.isEnabled = true
            btnSave.alpha = 1.0f
        }
    }

    override fun onBackPressed()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}