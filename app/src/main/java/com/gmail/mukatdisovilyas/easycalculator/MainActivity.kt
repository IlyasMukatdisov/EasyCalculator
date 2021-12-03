package com.gmail.mukatdisovilyas.easycalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.transition.TransitionManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.gmail.mukatdisovilyas.easycalculator.utils.*
import org.mariuszgromada.math.mxparser.Expression
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt


class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener
{

    /*companion object
    {
        private const val TAG = "MainActivity"

    }*/

    private lateinit var llMenu: LinearLayout
    private lateinit var llMain: LinearLayout
    private lateinit var llExpression: LinearLayout
    private lateinit var llButtons: LinearLayout
    private lateinit var cardView: CardView
    private lateinit var clExp: ConstraintLayout


    private lateinit var edtSecond: EditText
    private lateinit var edtMain: EditText
    private lateinit var btnMenu: ImageButton
    private lateinit var sinLinLay: LinearLayout
    private lateinit var logLinLay: LinearLayout
    private lateinit var btnAc: Button
    private lateinit var btnBrackets: Button
    private lateinit var btnPercent: Button
    private lateinit var btnDivision: Button
    private lateinit var btnSeven: Button
    private lateinit var btnEight: Button
    private lateinit var btnNine: Button
    private lateinit var btnMulti: Button
    private lateinit var btnFour: Button
    private lateinit var btnFive: Button
    private lateinit var btnSix: Button
    private lateinit var btnMinus: Button
    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnPlus: Button
    private lateinit var btnZero: Button
    private lateinit var btnDot: Button
    private lateinit var btnBackSpace: Button
    private lateinit var btnEqual: Button
    private lateinit var btnSquareRoot: Button
    private lateinit var btnExp: Button
    private lateinit var btnPi: Button
    private lateinit var btnFact: Button
    private lateinit var btnMore: Button
    private lateinit var btnSin: Button
    private lateinit var btnCos: Button
    private lateinit var btnTan: Button
    private lateinit var btnE: Button
    private lateinit var btnLg: Button
    private lateinit var btnLog2: Button
    private lateinit var btnLn: Button
    private lateinit var btnRad: Button
    private var isExpanded = false
    private var edtMainTextSize = 0f
    private var edtSecondTextSize = 0f
    private var prevOperator = ""
    private var isPrevEqual = false
    private var prevOperatorIndex = 0
    private var prevEqualIndex = 0


    private var mWidthPixels = 0
    private var mHeightPixels = 0

    private var btnTextSize = 0f

    private var currentNumDigits = 0

    private var lastNumber: Double = 0.0
    private var multipleEqualExp = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRealDeviceSizeInPixels()

        initViews()
        setOnClickListeners()

        setTextSizes()
        initPopUpMenu()

        if (!isEmptyCustomization())
        {
            customization()
        }
        else
        {
            setDefaultNumberColors()
            setDefaultTextColor()
        }

        setDefaultTheme()
        getPrevTextSize()

        /*if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        }*/
        
    }

    private fun setDefaultTheme()
    {
        if (isDarkThemeOn())
        {
            edtMain.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            edtSecond.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            llMain.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
            llExpression.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
            cardView.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            clExp.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            llButtons.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
            btnMenu.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_more_vert_white))

            edtMain.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            edtSecond.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        }
        else
        {
            edtMain.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            edtSecond.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            llMain.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            llExpression.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            cardView.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            clExp.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            llButtons.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            btnMenu.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_more_vert_black))

            edtMain.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            edtSecond.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        }

    }

    override fun onBackPressed()
    {
        moveTaskToBack(true)
    }

    private fun changeEdtSecondTextSize(strToAdd: String)
    {
        var textSize = edtSecond.textSize / resources.displayMetrics.scaledDensity


        if (edtSecond.textSize * edtSecond.text.length >= cardView.width && textSize > 30.0)
        {
            for (i in 0..strToAdd.length)
            {
                textSize -= if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2
                else 1
                edtSecond.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                if (textSize < 30.0)
                {
                    break
                }
            }
        }
    }

    private fun isAvailableToCalculate(strToAdd: String): Boolean
    {
        val temp = calculateFun(edtMain.text.toString())

        edtSecond.setText(temp)

        changeEdtSecondTextSize(strToAdd)

        if (edtSecond.text.toString() != Double.NaN.toString()) edtSecond.visibility = View.VISIBLE
        else edtSecond.visibility = View.INVISIBLE

        return true
    }

    private fun isEmptyCustomization(): Boolean
    {
        val dbHelper = CustomModelDatabaseHelper(this)
        return dbHelper.isCustomizationEmpty()
    }

    private fun customization()
    {
        val list = CustomModelDatabaseHelper(this).getAll()
        val numbersColor = list[0].numberButtonsColor
        val actionsColor = list[0].actionButtonsColor
        val acColor = list[0].acButtonColor
        val equalColor = list[0].equalButtonColor
        val textColor = list[0].textColor
        val shape = list[0].shape

        if (numbersColor.isNotEmpty())
        {
            btnZero.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnOne.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnTwo.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnThree.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnFour.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnFive.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnSix.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnSeven.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnEight.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnNine.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnDot.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnBackSpace.backgroundTintList = ColorStateList.valueOf(Color.parseColor(numbersColor))
        }
        else setDefaultNumberColors()

        if (actionsColor.isNotEmpty())
        {
            btnPlus.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnMinus.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnMulti.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnDivision.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnPercent.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnExp.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnPi.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnFact.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnSquareRoot.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnBrackets.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnMore.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnSin.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnCos.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnTan.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnE.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnLg.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnLn.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnLog2.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnRad.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
        }
        else setDefaultActionsColor()


        if (acColor.isNotEmpty()) btnAc.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(acColor))
        else btnAc.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.light_green)
        if (equalColor.isNotEmpty()) btnEqual.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(equalColor))
        else btnEqual.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.magenta)

        if (textColor.isNotEmpty()) setButtonsTextColor(textColor)
        else setDefaultTextColor()

        if (shape.isNotEmpty())
        {
            when (shape)
            {
                SHAPE_ROUNDED   ->
                {
                    setButtonsRounded()
                }
                SHAPE_RECTANGLE ->
                {
                    setButtonsRectangle()
                }
                SHAPE_CIRCLE    ->
                {
                    setButtonsCircle()
                }
            }

        }
        else setButtonsRounded()

    }

    private fun setDefaultTextColor()
    {
        if (isDarkThemeOn())
        {

            btnZero.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnOne.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnTwo.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnThree.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnFour.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnFive.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnSix.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnSeven.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnEight.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnNine.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnDot.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            btnBackSpace.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))


        }
        else
        {
            btnZero.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnOne.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnTwo.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnThree.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnFour.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnFive.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnSix.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnSeven.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnEight.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnNine.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnDot.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
            btnBackSpace.setTextColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        }

        btnPlus.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnMinus.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnMulti.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnDivision.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnPercent.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnExp.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnPi.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnFact.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnSquareRoot.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnBrackets.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnMore.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnSin.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnCos.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnTan.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnE.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnLg.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnLn.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnLog2.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnRad.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))

        btnAc.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))
        btnEqual.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)))


    }

    private fun setDefaultActionsColor()
    {
        btnPlus.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnMinus.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnMulti.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnDivision.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnPercent.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnExp.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnPi.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnFact.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnSquareRoot.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnBrackets.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnMore.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnSin.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnCos.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnTan.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnE.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnLg.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnLn.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnLog2.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnRad.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
    }

    private fun setDefaultNumberColors()
    {
        if (isDarkThemeOn())
        {

            btnZero.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnOne.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnTwo.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnThree.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnFour.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnFive.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnSix.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnSeven.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnEight.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnNine.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnDot.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            btnBackSpace.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            edtMain.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            edtSecond.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))

        }
        else
        {
            btnZero.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnOne.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnTwo.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnThree.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnFour.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnFive.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnSix.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnSeven.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnEight.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnNine.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnDot.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            btnBackSpace.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            edtMain.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            edtSecond.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
        }
    }

    private fun setButtonsTextColor(textColor: String)
    {
        btnZero.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnOne.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnTwo.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnThree.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnFour.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnFive.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnSix.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnSeven.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnEight.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnNine.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnDot.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnBackSpace.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))

        btnPlus.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnMinus.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnMulti.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnDivision.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnPercent.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnExp.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnPi.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnFact.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnSquareRoot.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnBrackets.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnMore.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnSin.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnCos.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnTan.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnE.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnLg.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnLn.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnLog2.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnRad.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))

        btnAc.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnEqual.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))

    }

    private fun setButtonsCircle()
    {
        btnZero.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnOne.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnTwo.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnThree.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnFour.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnFive.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnSix.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnSeven.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnEight.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnNine.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnDot.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnBackSpace.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)

        btnPlus.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnMinus.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnMulti.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnDivision.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnPercent.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnExp.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnPi.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnFact.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnSquareRoot.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnBrackets.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnMore.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnSin.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnCos.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnTan.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnE.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnLg.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnLn.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnLog2.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnRad.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnAc.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnEqual.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
    }

    private fun setButtonsRectangle()
    {
        btnZero.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnOne.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnTwo.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnThree.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnFour.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnFive.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnSix.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnSeven.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnEight.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnNine.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnDot.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnBackSpace.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)

        btnPlus.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnMinus.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnMulti.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnDivision.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnPercent.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnExp.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnPi.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnFact.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnSquareRoot.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnBrackets.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnMore.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnSin.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnCos.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnTan.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnE.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnLg.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnLn.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnLog2.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnRad.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnAc.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnEqual.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
    }

    private fun setButtonsRounded()
    {
        btnZero.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnOne.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnTwo.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnThree.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnFour.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnFive.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnSix.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnSeven.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnEight.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnNine.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnDot.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnBackSpace.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)

        btnPlus.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnMinus.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnMulti.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnDivision.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnPercent.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnExp.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnPi.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnFact.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnSquareRoot.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnBrackets.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnMore.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnSin.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnCos.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnTan.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnE.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnLg.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnLn.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnLog2.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnRad.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnAc.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnEqual.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)

    }

    @SuppressLint("DiscouragedPrivateApi", "ClickableViewAccessibility")
    private fun initPopUpMenu()
    {
        val popupMenu2 = PopupMenu(this, btnMenu)
        popupMenu2.inflate(R.menu.menu_main)

        popupMenu2.setOnMenuItemClickListener {
            when (it.itemId)
            {
                R.id.menu_to_history      ->
                {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_to_about        ->
                {
                    val cdd = CustomDialog(this)
                    val lp = WindowManager.LayoutParams()
                    lp.copyFrom(cdd.window?.attributes)
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                    cdd.show()
                    cdd.window?.attributes = lp/* AlertDialog.Builder(this).setTitle(resources.getString(R.string.app_name)).setMessage(
                         resources.getString(R.string.about_text)+ BuildConfig.VERSION_NAME
                     ).setPositiveButton(
                         resources.getString(R.string.write_me)
                     ) { _, _ ->
                         val intent2 = Intent(
                             this,
                             WebViewActivity()::class.java
                         )
                         intent2.putExtra(EXTRA_URL, getString(R.string.my_telegram))
                         startActivity(intent2)
                     }.setNegativeButton(
                         getString(R.string.btn_close)
                     ) { _, _ -> }.show()*/
                }
                R.id.menu_to_custom_color ->
                {
                    val intent = Intent(this, CustomizationActivity::class.java)
                    intent.putExtra(MAIN_TEXT_SIZE, btnTextSize)
                    startActivity(intent)
                }
                R.id.menu_to_graph        ->
                {
                    val intent = Intent(this, GraphActivity::class.java)
                    intent.putExtra(MAIN_TEXT_SIZE, btnTextSize)
                    startActivity(intent)
                }
            }
            false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            popupMenu2.setForceShowIcon(true)
        }
        else
        {
            try
            {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu2)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            }
            catch (e: Exception)
            {

            }
        }

        btnMenu.setOnClickListener {
            popupMenu2.show()
        }

        btnMenu.setOnTouchListener(this)
        llMenu.setOnTouchListener(this)

        llMenu.setOnClickListener {
            popupMenu2.show()
        }
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
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.alpha))
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.semi_gray))
                    }
                    MotionEvent.ACTION_UP   ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
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
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.alpha))
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.semi_gray))
                    }
                    MotionEvent.ACTION_UP   ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
                        view.performClick()
                    }
                }
            }

        }
        return true
    }

    private fun getPrevTextSize()
    {
        edtMainTextSize = edtMain.textSize / resources.displayMetrics.scaledDensity
        edtSecondTextSize = edtSecond.textSize / resources.displayMetrics.scaledDensity
    }


    /*private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, llMain).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }*/

    /*private fun setFullScreen()
    {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }*/

    private fun setTextSizes()
    {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val x = (mWidthPixels / dm.xdpi).toDouble().pow(2.0)
        val y = (mHeightPixels / dm.ydpi).toDouble().pow(2.0)
        val screenInches = sqrt(x + y)

        if (screenInches < 5.0)
        {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                if (mWidthPixels in 0..480) btnTextSize = 14 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 12 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 10 * resources.displayMetrics.density
            }
            else
            {
                if (mWidthPixels in 0..480) btnTextSize = 12 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 10 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 8 * resources.displayMetrics.density
            }
        }

        if (screenInches in 5.0..7.0)
        {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                if (mWidthPixels in 0..480) btnTextSize = 28 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 20 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 12 * resources.displayMetrics.density
                if (mWidthPixels in 1081..1600) btnTextSize = 10 * resources.displayMetrics.density
                if (mWidthPixels in 1601..2560) btnTextSize = 9 * resources.displayMetrics.density
            }
            else
            {
                if (mWidthPixels in 0..480) btnTextSize = 24 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 14 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 9 * resources.displayMetrics.density
                if (mWidthPixels in 1081..1600) btnTextSize = 7 * resources.displayMetrics.density
                if (mWidthPixels in 1601..2560) btnTextSize = 5 * resources.displayMetrics.density
            }
        }

        if (screenInches > 7.0)
        {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                if (mWidthPixels in 0..480) btnTextSize = 28 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 22 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 19 * resources.displayMetrics.density
                if (mWidthPixels in 1081..1600) btnTextSize = 18 * resources.displayMetrics.density
                if (mWidthPixels in 1601..2160) btnTextSize = 15 * resources.displayMetrics.density
                if (mWidthPixels in 2560..4000) btnTextSize = 12 * resources.displayMetrics.density
            }
            else
            {
                if (mWidthPixels in 0..480) btnTextSize = 24 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 20 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 16 * resources.displayMetrics.density
                if (mWidthPixels in 1081..1600) btnTextSize = 15 * resources.displayMetrics.density
                if (mWidthPixels in 1601..2160) btnTextSize = 13 * resources.displayMetrics.density
                if (mWidthPixels in 2560..4000) btnTextSize = 11 * resources.displayMetrics.density
            }
        }

        btnZero.textSize = btnTextSize
        btnOne.textSize = btnTextSize
        btnTwo.textSize = btnTextSize
        btnThree.textSize = btnTextSize
        btnFour.textSize = btnTextSize
        btnFive.textSize = btnTextSize
        btnSix.textSize = btnTextSize
        btnSeven.textSize = btnTextSize
        btnEight.textSize = btnTextSize
        btnNine.textSize = btnTextSize
        btnMore.textSize = btnTextSize
        btnPlus.textSize = btnTextSize
        btnMinus.textSize = btnTextSize
        btnMulti.textSize = btnTextSize
        btnDivision.textSize = btnTextSize
        btnPercent.textSize = btnTextSize
        btnDot.textSize = btnTextSize
        btnAc.textSize = btnTextSize
        btnBrackets.textSize = btnTextSize
        btnBackSpace.textSize = btnTextSize
        btnEqual.textSize = btnTextSize
        btnSquareRoot.textSize = btnTextSize
        btnExp.textSize = btnTextSize
        btnPi.textSize = btnTextSize
        btnFact.textSize = btnTextSize
        btnSin.textSize = btnTextSize
        btnCos.textSize = btnTextSize
        btnTan.textSize = btnTextSize
        btnE.textSize = btnTextSize
        btnLog2.textSize = btnTextSize
        btnLg.textSize = btnTextSize
        btnLn.textSize = btnTextSize
        btnRad.textSize = btnTextSize
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            edtMain.textSize = btnTextSize * 13 / 8
            edtSecond.textSize = btnTextSize * 4 / 3
        }
        else
        {
            edtMain.textSize = btnTextSize * 5 / 2
            edtSecond.textSize = btnTextSize * 3 / 2
        }

    }

    private fun initViews()
    {

        llMenu = findViewById(R.id.ll_menu)

        btnAc = findViewById(R.id.btn_ac)
        btnBrackets = findViewById(R.id.btn_brackets)
        btnPercent = findViewById(R.id.btn_percent)
        btnDivision = findViewById(R.id.btn_division)
        btnSeven = findViewById(R.id.btn_seven)
        btnEight = findViewById(R.id.btn_eight)
        btnNine = findViewById(R.id.btn_nine)
        btnMulti = findViewById(R.id.btn_multi)
        btnFour = findViewById(R.id.btn_four)
        btnFive = findViewById(R.id.btn_five)
        btnSix = findViewById(R.id.btn_six)
        btnMinus = findViewById(R.id.btn_minus)
        btnOne = findViewById(R.id.btn_one)
        btnTwo = findViewById(R.id.btn_two)
        btnThree = findViewById(R.id.btn_three)
        btnPlus = findViewById(R.id.btn_plus)
        btnZero = findViewById(R.id.btn_zero)
        btnDot = findViewById(R.id.btn_dot)
        btnBackSpace = findViewById(R.id.btn_backspace)
        btnEqual = findViewById(R.id.btn_equal)
        edtMain = findViewById(R.id.edt_main)
        btnSquareRoot = findViewById(R.id.btn_square_root)
        btnExp = findViewById(R.id.btn_exp)
        btnPi = findViewById(R.id.btn_pi)
        btnFact = findViewById(R.id.btn_fact)
        btnMore = findViewById(R.id.btn_more)
        btnSin = findViewById(R.id.btn_sin)
        btnCos = findViewById(R.id.btn_cos)
        btnTan = findViewById(R.id.btn_tan)
        btnE = findViewById(R.id.btn_e)
        btnLog2 = findViewById(R.id.btn_log2)
        btnLg = findViewById(R.id.btn_log10)
        btnLn = findViewById(R.id.btn_ln)
        btnRad = findViewById(R.id.btn_rad)
        sinLinLay = findViewById(R.id.sin_ll)
        logLinLay = findViewById(R.id.log_ll)
        btnMenu = findViewById(R.id.btn_menu)
        edtSecond = findViewById(R.id.edt_second)

        llMain = findViewById(R.id.ll_main)
        llExpression = findViewById(R.id.ll_expression)
        cardView = findViewById(R.id.cardView)
        clExp = findViewById(R.id.cl_exp)
        llButtons = findViewById(R.id.ll_buttons)
    }

    private fun setOnClickListeners()
    {
        btnZero.setOnClickListener(this)
        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
        btnThree.setOnClickListener(this)
        btnFour.setOnClickListener(this)
        btnFive.setOnClickListener(this)
        btnSix.setOnClickListener(this)
        btnSeven.setOnClickListener(this)
        btnEight.setOnClickListener(this)
        btnNine.setOnClickListener(this)
        btnPercent.setOnClickListener(this)
        btnDivision.setOnClickListener(this)
        btnMulti.setOnClickListener(this)
        btnPlus.setOnClickListener(this)
        btnMinus.setOnClickListener(this)
        btnDot.setOnClickListener(this)
        btnAc.setOnClickListener(this)
        btnBrackets.setOnClickListener(this)
        btnBackSpace.setOnClickListener(this)
        btnEqual.setOnClickListener(this)
        btnSquareRoot.setOnClickListener(this)
        btnExp.setOnClickListener(this)
        btnPi.setOnClickListener(this)
        btnFact.setOnClickListener(this)
        btnMore.setOnClickListener(this)
        btnSin.setOnClickListener(this)
        btnCos.setOnClickListener(this)
        btnTan.setOnClickListener(this)
        btnE.setOnClickListener(this)
        btnLog2.setOnClickListener(this)
        btnLg.setOnClickListener(this)
        btnLn.setOnClickListener(this)
        btnRad.setOnClickListener(this)

        edtMain.showSoftInputOnFocus = false
        edtSecond.showSoftInputOnFocus = false

    }


    private fun getRealDeviceSizeInPixels()
    {
        val windowManager: WindowManager = windowManager
        val display: Display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)


        // since SDK_INT = 1;
        mWidthPixels = displayMetrics.widthPixels
        mHeightPixels = displayMetrics.heightPixels

        // includes window decorations (statusBar bar/menu bar)
        try
        {
            mWidthPixels = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
            mHeightPixels = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
        }
        catch (ignored: java.lang.Exception)
        {
        }

        // includes window decorations (statusBar bar/menu bar)
        try
        {
            val realSize = Point()
            Display::class.java.getMethod("getRealSize", Point::class.java)
                .invoke(display, realSize)
            mWidthPixels = realSize.x
            mHeightPixels = realSize.y
        }
        catch (ignored: java.lang.Exception)
        {
        }
    }


    override fun onClick(v: View?)
    {
        when (v?.id)
        {
            R.id.btn_zero        ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("0")) isAvailableToCalculate("0")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_one         ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("1")) isAvailableToCalculate("1")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_two         ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("2")) isAvailableToCalculate("2")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_three       ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("3")) isAvailableToCalculate("3")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_four        ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("4")) isAvailableToCalculate("4")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_five        ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("5")) isAvailableToCalculate("5")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_six         ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("6")) isAvailableToCalculate("6")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_seven       ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("7")) isAvailableToCalculate("7")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_eight       ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("8")) isAvailableToCalculate("8")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_nine        ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("9")) isAvailableToCalculate("9")
                isPrevEqual = false
                currentNumDigits++
            }

            R.id.btn_percent     ->
            {
                if (checkForOperator()) backspaceFun()
                if (updateEdtMain("%")) isAvailableToCalculate("%")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_division    ->
            {
                if (checkForOperator()) backspaceFun()
                if (updateEdtMain("÷")) isAvailableToCalculate("÷")
                prevOperator = "÷"
                prevOperatorIndex = edtMain.text.lastIndex
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_multi       ->
            {
                if (checkForOperator()) backspaceFun()
                if (updateEdtMain("×")) isAvailableToCalculate("×")
                prevOperator = "×"
                prevOperatorIndex = edtMain.text.lastIndex
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_plus        ->
            {
                if (checkForOperator()) backspaceFun()
                if (updateEdtMain("+")) isAvailableToCalculate("+")
                prevOperator = "+"
                prevOperatorIndex = edtMain.text.lastIndex
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_minus       ->
            {
                if (checkForOperator()) backspaceFun()
                if (updateEdtMain("-")) isAvailableToCalculate("-")
                prevOperator = "-"
                prevOperatorIndex = edtMain.text.lastIndex
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_dot         ->
            {
                if (isDotInsertable()) if (updateEdtMain(".")) isAvailableToCalculate(".")
                isPrevEqual = false
            }


            R.id.btn_ac          ->
            {
                clear()
                isPrevEqual = false
                prevOperator = ""
                currentNumDigits = 0
            }

            R.id.btn_backspace   ->
            {
                backspaceFun()
                isPrevEqual = false
            }

            R.id.btn_brackets    ->
            {
                if (isPrevEqual) clear()
                if (addBrackets()) isAvailableToCalculate("{")
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_equal       ->
            {
                if (!isPrevEqual)
                {
                    multipleEqualExp = edtMain.text.toString()
                    try
                    {
                        if (edtMain.text.isNotEmpty() && prevOperator != "" && !checkForOperator(
                                edtMain.text[edtMain.text.lastIndex])
                        ) lastNumber =
                            edtMain.text.toString().substring(prevOperatorIndex + 1).toDouble()
                    }
                    catch (e: Exception)
                    {
                    }
                }
                else
                {
                    if (isPrevEqual && prevOperator != "")
                    {
                        multipleEqualExp =
                            if (lastNumber % 1 == 0.0) "$multipleEqualExp$prevOperator${lastNumber.toInt()}"
                            else "$multipleEqualExp$prevOperator$lastNumber"

                        edtSecond.setText(multipleEqualExp)
                        edtSecond.text
                        changeEdtSecondTextSize(edtSecond.text.toString())
                        edtMain.setText(calculateFun(multipleEqualExp))
                        changeEdtMainTextSize(edtMain.text.toString())
                        currentNumDigits = 0
                        edtMain.setSelection(edtMain.text.length)
                        isPrevEqual = true
                        return
                    }/*else
                    {
                        if (checkForOperator())
                        {
                            val temp = edtResult.text.toString().substring(prevEqualIndex)
                            updateEdtPrevExp(temp)
                        }
                    }*/
                }
                edtSecond.setText(edtMain.text.toString())
                changeEdtSecondTextSize(edtSecond.text.toString())
                val result = calculateFun(edtMain.text.toString())
                edtMain.setText(result)
                changeEdtMainTextSize(edtMain.text.toString())
                edtMain.setSelection(result.length)
                currentNumDigits = 0
                isPrevEqual = true
            }

            R.id.btn_square_root ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("√(")) isAvailableToCalculate("√(")
                prevOperator = ""
                isPrevEqual = false

                currentNumDigits = 0
            }

            R.id.btn_exp         ->
            {
                if (checkForOperator())
                {
                    backspaceFun()
                }
                if (updateEdtMain("^")) isAvailableToCalculate("^")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_pi          ->
            {
                if (isPrevEqual) clear()
                if (edtMain.text.isNotEmpty())
                {
                    if (edtMain.text[edtMain.text.toString().length - 1] == 'e' || edtMain.text[edtMain.text.toString().length - 1] == 'π') return
                }
                if (updateEdtMain("π")) isAvailableToCalculate("π")
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_fact        ->
            {
                if (checkForOperator())
                {
                    backspaceFun()
                }
                if (updateEdtMain("!")) isAvailableToCalculate("!")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_more        ->
            {
                isExpanded = !isExpanded
                expand()
                isPrevEqual = false

            }
            R.id.btn_sin         ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("sin(")) isAvailableToCalculate("sin(")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_cos         ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("cos(")) isAvailableToCalculate("cos(")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_tan         ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("tan(")) isAvailableToCalculate("tan(")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_e           ->
            {
                if (isPrevEqual) clear()

                if (edtMain.text.isNotEmpty())
                {
                    if (edtMain.text[edtMain.text.toString().length - 1] == 'e' || edtMain.text[edtMain.text.toString().length - 1] == 'π') return
                }
                if (updateEdtMain("e")) isAvailableToCalculate("e")
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_log2        ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("log2(")) isAvailableToCalculate("log2(")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_log10       ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("log10(")) isAvailableToCalculate("log10(")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_ln          ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("ln(")) isAvailableToCalculate("ln(")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

            R.id.btn_rad         ->
            {
                if (isPrevEqual) clear()
                if (updateEdtMain("rad(")) isAvailableToCalculate("rad(")
                prevOperator = ""
                isPrevEqual = false
                currentNumDigits = 0
            }

        }
    }

    private fun clear()
    {
        edtMain.setText("")
        edtSecond.setText("")
        edtMain.setTextSize(TypedValue.COMPLEX_UNIT_SP, edtMainTextSize)
        edtSecond.setTextSize(TypedValue.COMPLEX_UNIT_SP, edtSecondTextSize)
    }


    private fun expand()
    {
        if (isExpanded)
        {
            btnMore.text = getString(R.string.btn_more_expanded)
            TransitionManager.beginDelayedTransition(llMain)
            sinLinLay.visibility = View.VISIBLE
            logLinLay.visibility = View.VISIBLE
        }
        else
        {
            btnMore.text = getString(R.string.btn_more_not_expanded)
            TransitionManager.beginDelayedTransition(llMain)
            sinLinLay.visibility = View.GONE
            logLinLay.visibility = View.GONE
        }
    }

    private fun calculateFun(exp: String): String
    {
        var userExp = checkForClosedBrackets(exp)
        val dbExp = userExp
        val dbHelper = HistoryDatabaseHelper(this)

        userExp = userExp.replace("√", "sqrt")
        userExp = userExp.replace("÷", "/")
        userExp = userExp.replace("×", "*")
        userExp = userExp.replace("π", "pi")
        userExp = userExp.replace("%", "#")

        val expression = Expression(userExp)
        val value = expression.calculate()

        var result = value.toString()

        try
        {
            if (BigDecimal.valueOf(value).scale() > 8)
            {
                result = String.format("%.7f", value)
            }
        }
        catch (e: Exception)
        {

        }

        if (result[result.length - 1] == '0' && result[result.length - 2] == '.')
        {
            result = result.removeRange(result.length - 2, result.length)
        }

        prevEqualIndex = edtMain.text.lastIndex

        if (result != "Infinity" && result != "NaN")
        {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: String = sdf.format(Date())
            dbHelper.addOne(dbExp, result, date)
        }
        return result
    }

    private fun checkForClosedBrackets(s: String): String
    {
        var exp = s
        val textLength = exp.length
        var openBracketCount = 0
        var closeBracketCount = 0
        for (i in 0 until textLength)
        {
            if (edtMain.text.toString().substring(i, i + 1) == "(")
            {
                openBracketCount++
            }
            if (edtMain.text.toString().substring(i, i + 1) == ")")
            {
                closeBracketCount++
            }
        }
        while (openBracketCount > closeBracketCount)
        {
            exp = "$exp)"
            closeBracketCount++
        }
        return exp
    }


    private fun isDotInsertable(): Boolean
    {
        if (edtMain.text.isEmpty()) return false

        val cursor = edtMain.selectionStart
        var i: Int = if (cursor == 0) cursor
        else cursor - 1
        var number = ""
        while (i >= 0)
        {
            if (!(checkForDigit(edtMain.text[i]) || edtMain.text[i] == '.')) break
            number = "${edtMain.text[i]}$number"
            i--
        }
        i = if (cursor == edtMain.text.lastIndex) cursor - 1
        else cursor
        while (i <= edtMain.text.lastIndex)
        {
            if (!(checkForDigit(edtMain.text[i]) || edtMain.text[i] == '.')) break
            number = "$number${edtMain.text[i]}"
            i++
        }
        if (number.isEmpty()) return false
        return !isStringHasDot(number)
    }

    private fun checkForDigit(c: Char): Boolean
    {
        when (c)
        {
            '0' -> return true
            '1' -> return true
            '2' -> return true
            '3' -> return true
            '4' -> return true
            '5' -> return true
            '6' -> return true
            '7' -> return true
            '8' -> return true
            '9' -> return true
        }
        return false
    }

    private fun checkForNumber(c: Char): Boolean
    {
        when (c)
        {
            '0' -> return true
            '1' -> return true
            '2' -> return true
            '3' -> return true
            '4' -> return true
            '5' -> return true
            '6' -> return true
            '7' -> return true
            '8' -> return true
            '9' -> return true
            '.' -> return true
        }
        return false
    }


    private fun isStringHasDot(s: String): Boolean
    {
        for (element in s)
        {
            if (element == '.') return true
        }
        return false
    }


    private fun checkForOperator(): Boolean
    {
        val cursor = edtMain.selectionStart
        if (cursor == 0)
        {
            return false
        }
        var needToChange = false
        when (edtMain.text.toString()[cursor - 1])
        {
            '+' -> needToChange = true
            '-' -> needToChange = true
            '×' -> needToChange = true
            '÷' -> needToChange = true
            '%' -> needToChange = true
            '^' -> needToChange = true
            '!' -> needToChange = true
        }
        return needToChange
    }

    private fun checkForOperator(c: Char): Boolean
    {
        when (c)
        {
            '+' -> return true
            '-' -> return true
            '×' -> return true
            '÷' -> return true
            '%' -> return true
            '^' -> return true
            '!' -> return true
            '.' -> return true
            'π' -> return true
            'e' -> return true
            '(' -> return true
            ')' -> return true
        }
        return false
    }

    private fun addBrackets(): Boolean
    {
        val cursorPos = edtMain.selectionStart
        val textLength = edtMain.text.length
        var openBracketCount = 0
        var closeBracketCount = 0

        if (textLength == 0)
        {
            updateEdtMain("(")
            edtMain.setSelection(cursorPos + 1)
            return true
        }

        if (edtMain.text.toString()[cursorPos - 1] == '+' || edtMain.text.toString()[cursorPos - 1] == '-' || edtMain.text.toString()[cursorPos - 1] == '×' || edtMain.text.toString()[cursorPos - 1] == '÷' || edtMain.text.toString()[cursorPos - 1] == '^' || edtMain.text.toString()[cursorPos - 1] == '!' || edtMain.text.toString()[cursorPos - 1] == '√')
        {
            updateEdtMain("(")
            edtMain.setSelection(cursorPos + 1)
            return true
        }

        for (i in 0 until cursorPos)
        {
            if (edtMain.text.toString().substring(i, i + 1) == "(")
            {
                openBracketCount++
            }
            if (edtMain.text.toString().substring(i, i + 1) == ")")
            {
                closeBracketCount++
            }
        }

        if (openBracketCount == closeBracketCount || edtMain.text.toString()
                .substring(textLength - 1, textLength) == "("
        )
        {
            if (updateEdtMain("(")) edtMain.setSelection(cursorPos + 1)
        }
        else if (closeBracketCount < openBracketCount && edtMain.text.toString()
                .substring(textLength - 1, textLength) != "("
        )
        {
            if (updateEdtMain(")")) edtMain.setSelection(cursorPos + 1)
        }
        return false
    }


    private fun digitLimit(): Int
    {
        var currentNumDigits = 0
        if (edtMain.text.isEmpty()) return currentNumDigits
        val position = edtMain.selectionStart
        var i = if (position == 0) position
        else position - 1

        while (checkForNumber(edtMain.text[i]) || edtMain.text[i] == ',')
        {
            if (edtMain.text[i] == ',')
            {
                i--
                if (i < 0) break
                continue
            }
            currentNumDigits++
            i--
            if (i < 0) break
        }

        i = if (position == edtMain.text.length) position - 1
        else position

        while (checkForNumber(edtMain.text[i]) || edtMain.text[i] == ',')
        {
            if (edtMain.text[i] == ',')
            {
                i++
                if (i > edtMain.text.lastIndex) break
                continue
            }

            currentNumDigits++
            i++
            if (i > edtMain.text.lastIndex) break
        }

        return currentNumDigits
    }

    private fun updateEdtMain(strToAdd: String): Boolean
    {

        if (strToAdd.length == 1 && !checkForOperator(strToAdd[0]) && digitLimit() >= MAX_DIGITS)
        {
            return false
        }


        var prevString = edtMain.text.toString()

        if (prevString == "NaN" || prevString == "Infinity")
        {
            prevString = ""
            clear()
        }

        val cursorPos = edtMain.selectionStart
        val leftString = prevString.substring(0, cursorPos)
        val rightString = prevString.substring(cursorPos)
        edtMain.setText(String.format("%s%s%s", leftString, strToAdd, rightString))
        edtMain.setSelection(cursorPos + strToAdd.length)

        changeEdtMainTextSize(strToAdd)

        return true
    }

    private fun changeEdtMainTextSize(strToAdd: String)
    {
        var textSize = edtMain.textSize / resources.displayMetrics.scaledDensity

        if (edtMain.textSize * edtMain.text.length >= cardView.width && textSize > 40.0)
        {
            for (i in 0..strToAdd.length)
            {
                textSize -= if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2
                else 1
                edtMain.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                if (textSize < 40.0)
                {
                    break
                }
            }
        }
    }

    private fun increaseEdtMainTextSize(countDeletedChars: Int)
    {
        var edtResTextSize = edtMain.textSize / resources.displayMetrics.scaledDensity

        if (edtResTextSize < edtMainTextSize)
        {
            for (i in 0..countDeletedChars)
            {
                if (edtResTextSize >= edtMainTextSize) break
                edtResTextSize += if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2
                else 1
                edtMain.textSize = edtResTextSize
            }
        }
    }

    private fun changeLastOperator()
    {
        var i = edtMain.text.lastIndex

        while (i >= 0)
        {
            if (edtMain.text[i] == '+' || edtMain.text[i] == '-' || edtMain.text[i] == '×' || edtMain.text[i] == '÷')
            {
                prevOperator = edtMain.text[i].toString()
                prevOperatorIndex = i
            }
            i--
        }
    }


    private fun backspaceFun()
    {
        if (edtMain.length() == 0 || edtMain.selectionStart == 0 || digitLimit() >= MAX_DIGITS)
        {
            return
        }

        if (edtMain.text.toString() == "NaN" || edtMain.text.toString() == "Infinity")
        {
            clear()
            return
        }

        if (edtMain.length() >= 3) if (mathFunDelete())
        {
            changeLastOperator()
            isAvailableToCalculate("")
            return
        }


        val cursorPos = edtMain.selectionStart
        val textLength = edtMain.text.length

        if (cursorPos != 0 && textLength != 0)
        {
            deleteLast(edtMain)
            increaseEdtMainTextSize(1)
        }

        changeLastOperator()
        isAvailableToCalculate("")


    }

    private fun deleteLast(edt: EditText)
    {
        val cursorPos = edt.selectionStart
        val selection: SpannableStringBuilder = edt.text as SpannableStringBuilder
        selection.replace(cursorPos - 1, cursorPos, "")
        edt.text = selection
        edt.setSelection(cursorPos - 1)
    }

    private fun mathFunDelete(): Boolean
    {
        val pos = edtMain.selectionStart
        val lastIndex = edtMain.text.lastIndex

        when (edtMain.text[pos - 1])
        {
            's' ->
            {
                if (edtMain.length() >= 4 && pos - 1 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 1, pos + 3) == "sin(")
                    {
                        edtMain.text.replace(pos - 1, pos + 3, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
                if (edtMain.length() >= 4 && pos - 3 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 3, pos + 1) == "cos(")
                    {
                        edtMain.text.replace(pos - 3, pos + 1, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }

            }

            'i' ->
            {
                if (edtMain.length() >= 4 && pos - 2 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 2, pos + 2) == "sin(")
                    {
                        edtMain.text.replace(pos - 2, pos + 2, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
            }

            'n' ->
            {
                if (edtMain.length() >= 4 && pos - 3 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 3, pos + 1) == "sin(")
                    {
                        edtMain.text.replace(pos - 3, pos + 1, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                    if (edtMain.text.substring(pos - 3, pos + 1) == "tan(")
                    {
                        edtMain.text.replace(pos - 3, pos + 1, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
                if (pos - 2 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 2, pos + 1) == "ln(")
                    {
                        edtMain.text.replace(pos - 2, pos + 1, "")
                        increaseEdtMainTextSize(3)
                        return true
                    }
                }
            }

            'c' ->
            {
                if (edtMain.length() >= 4 && pos - 1 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 1, pos + 3) == "cos(")
                    {
                        edtMain.text.replace(pos - 1, pos + 3, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
            }

            'o' ->
            {
                if (edtMain.length() >= 4 && pos - 2 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 2, pos + 2) == "cos(")
                    {
                        edtMain.text.replace(pos - 2, pos + 2, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
                if (edtMain.length() >= 5 && pos - 2 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 2, pos + 3) == "log2(")
                    {
                        edtMain.text.replace(pos - 2, pos + 3, "")
                        increaseEdtMainTextSize(5)
                        return true
                    }
                }
                if (edtMain.length() >= 6 && pos - 2 >= 0 && pos + 4 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 2, pos + 4) == "log10(")
                    {
                        edtMain.text.replace(pos - 2, pos + 4, "")
                        increaseEdtMainTextSize(6)
                        return true
                    }
                }
            }

            't' ->
            {
                if (edtMain.length() >= 4 && pos - 1 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 1, pos + 3) == "tan(")
                    {
                        edtMain.text.replace(pos - 1, pos + 3, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
            }

            'a' ->
            {
                if (edtMain.length() >= 4 && pos - 2 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 2,
                            pos + 2) == "tan(" || edtMain.text.substring(pos - 2, pos + 2) == "rad("
                    )
                    {
                        edtMain.text.replace(pos - 2, pos + 2, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
            }

            'l' ->
            {
                if (pos - 1 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 1, pos + 2) == "ln(")
                    {
                        edtMain.text.replace(pos - 1, pos + 2, "")
                        increaseEdtMainTextSize(3)
                        return true
                    }
                }
                if (edtMain.length() >= 5 && pos - 1 >= 0 && pos + 4 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 1, pos + 4) == "log2(")
                    {
                        edtMain.text.replace(pos - 1, pos + 4, "")
                        increaseEdtMainTextSize(5)
                        return true
                    }
                }
                if (edtMain.length() >= 6 && pos - 1 >= 0 && pos + 5 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 1, pos + 5) == "log10(")
                    {
                        edtMain.text.replace(pos - 1, pos + 5, "")
                        increaseEdtMainTextSize(6)
                        return true
                    }
                }
            }

            'g' ->
            {
                if (edtMain.length() >= 5 && pos - 3 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 3, pos + 2) == "log2(")
                    {
                        edtMain.text.replace(pos - 3, pos + 2, "")
                        increaseEdtMainTextSize(5)
                        return true
                    }
                }
                if (edtMain.length() >= 6 && pos - 3 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 3, pos + 3) == "log10(")
                    {
                        edtMain.text.replace(pos - 3, pos + 3, "")
                        increaseEdtMainTextSize(5)
                        return true
                    }
                }
            }

            '1' ->
            {
                if (edtMain.length() >= 6 && pos - 4 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 4, pos + 2) == "log10(")
                    {
                        edtMain.text.replace(pos - 4, pos + 2, "")
                        increaseEdtMainTextSize(6)
                        return true
                    }
                }
            }

            '0' ->
            {
                if (edtMain.length() >= 6 && pos - 5 >= 0 && pos + 1 <= lastIndex + 1)
                {

                    if (edtMain.text.substring(pos - 5, pos + 1) == "log10(")
                    {
                        edtMain.text.replace(pos - 5, pos + 1, "")
                        increaseEdtMainTextSize(6)
                        return true
                    }
                }
            }

            '2' ->
            {
                if (edtMain.length() >= 5 && pos - 4 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 4, pos + 1) == "log2(")
                    {
                        edtMain.text.replace(pos - 4, pos + 1, "")
                        increaseEdtMainTextSize(5)
                        return true
                    }
                }
            }

            'r' ->
            {
                if (edtMain.length() >= 4 && pos - 1 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 1, pos + 3) == "rad(")
                    {
                        edtMain.text.replace(pos - 1, pos + 3, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
            }

            'd' ->
            {
                if (edtMain.length() >= 4 && pos - 3 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 3, pos + 1) == "rad(")
                    {
                        edtMain.text.replace(pos - 3, pos + 1, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
            }

            '(' ->
            {

                if (pos - 3 >= 0 && pos <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 3, pos) == "ln(")
                    {
                        edtMain.text.replace(pos - 3, pos, "")
                        increaseEdtMainTextSize(3)
                        return true
                    }
                }
                if (edtMain.length() >= 4 && pos - 4 >= 0 && pos <= lastIndex + 1)
                {
                    if (edtMain.text.substring(pos - 4, pos) == "sin(" || edtMain.text.substring(
                            pos - 4, pos) == "cos(" || edtMain.text.substring(pos - 4,
                            pos) == "tan(" || edtMain.text.substring(pos - 4, pos) == "rad("
                    )
                    {
                        edtMain.text.replace(pos - 4, pos, "")
                        increaseEdtMainTextSize(4)
                        return true
                    }
                }
                if (edtMain.length() >= 5 && pos - 5 >= 0 && pos <= lastIndex + 1 + 1)
                {
                    if (edtMain.text.substring(pos - 5, pos) == "log2(")
                    {
                        edtMain.text.replace(pos - 5, pos, "")
                        increaseEdtMainTextSize(5)
                        return true
                    }
                }
                if (edtMain.length() >= 6 && pos - 6 >= 0 && pos <= lastIndex + 1) if (edtMain.text.substring(
                        pos - 6, pos) == "log10("
                )
                {
                    edtMain.text.replace(pos - 6, pos, "")
                    increaseEdtMainTextSize(6)
                    return true
                }
            }


        }
        return false
    }


    private fun Context.isDarkThemeOn(): Boolean
    {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}

