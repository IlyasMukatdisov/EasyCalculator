package com.gmail.mukatdisovilyas.easycalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Insets
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.gmail.mukatdisovilyas.easycalculator.utils.*
import org.mariuszgromada.math.mxparser.Expression
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt
import android.view.WindowInsets

import android.view.WindowMetrics


class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val TAG = "MainActivity"
    }


    private lateinit var llMain: LinearLayout
    private lateinit var llExpression: LinearLayout
    private lateinit var llButtons: LinearLayout
    private lateinit var cardView: CardView
    private lateinit var clExp: ConstraintLayout


    private lateinit var edtTempResult: EditText
    private lateinit var btnMenu: ImageButton
    private lateinit var sinLinLay: LinearLayout
    private lateinit var logLinLay: LinearLayout
    private lateinit var edtResult: EditText
    private lateinit var edtPrevExp: EditText
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
    private var edtResultTextSize = 0f
    private var edtPrevTextSize = 0f
    private var prevOperator = ""
    private var isPrevEqual = false
    private var prevOperatorIndex = 0
    private var prevEqualIndex = 0

    private var mWidthPixels = 0
    private var mHeightPixels = 0

    private var btnTextSize = 0f

    private var currentNumDigits = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRealDeviceSizeInPixels()

        initViews()
        setOnClickListeners()

        setTextSizes()
        initPopUpMenu()

        if (!isEmptyCustomization()) {
            customization()
        } else {
            if (isDarkThemeOn()) {
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
                edtPrevExp.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                edtResult.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
                edtTempResult.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))

                btnZero.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnOne.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnTwo.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnThree.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnFour.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnFive.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnSix.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnSeven.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnEight.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnNine.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnDot.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )
                btnBackSpace.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                )


            }
        }

        if (isDarkThemeOn()) {
            edtPrevExp.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            edtResult.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
            edtTempResult.backgroundTintList =
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

            edtResult.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            )
            edtTempResult.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            )
            edtPrevExp.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            )

        }

        edtResult.showSoftInputOnFocus = false
        edtPrevExp.showSoftInputOnFocus = false
        edtTempResult.showSoftInputOnFocus = false

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        }

        getPrevTextSize()


    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun isAvailableToCalculate(): Boolean {
        var userExp = edtResult.text.toString()

        val textLength = userExp.length
        var openBracketCount = 0
        var closeBracketCount = 0
        for (i in 0 until textLength) {
            if (userExp.substring(i, i + 1) == "(") {
                openBracketCount++
            }
            if (userExp.substring(i, i + 1) == ")") {
                closeBracketCount++
            }
        }
        while (openBracketCount > closeBracketCount) {
            userExp = "$userExp)"
            closeBracketCount++
        }


        userExp = userExp.replace("√", "sqrt")
        userExp = userExp.replace("÷", "/")
        userExp = userExp.replace("×", "*")
        userExp = userExp.replace("π", "pi")
        userExp = userExp.replace("%", "#")

        val expression = Expression(userExp)
        val value = expression.calculate()

        return if (value.isNaN()) {
            edtTempResult.visibility = View.GONE
            false
        } else {
            var temp = value.toString()
            try {
                if (BigDecimal.valueOf(value).scale() > 8) {
                    temp = String.format("%.7f", value)
                }

                if (!checkForDot(temp)) {
                    temp = value.toInt().toString()
                }
                if (temp[temp.length - 1] == '0' && temp[temp.length - 2] == '.') {
                    temp = temp.removeRange(temp.length - 2, temp.length)
                }
            } catch (e: java.lang.Exception) {
                return false
            }

            /* var textSize = edtTempResult.textSize / resources.displayMetrics.scaledDensity

             if (edtTempResult.width +textSize > mWidthPixels)
             {
                 textSize -= 3
                 edtTempResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
             }*/

            edtTempResult.setText(temp)
            edtTempResult.visibility = View.VISIBLE

            true
        }
    }

    private fun isEmptyCustomization(): Boolean {
        val dbHelper = CustomModelDatabaseHelper(this)
        return dbHelper.isCustomizationEmpty()
    }

    private fun customization() {
        val list = CustomModelDatabaseHelper(this).getAll()
        val numbersColor = list[0].numberButtonsColor
        val actionsColor = list[0].actionButtonsColor
        val acColor = list[0].acButtonColor
        val equalColor = list[0].equalButtonColor
        val textColor = list[0].textColor
        val shape = list[0].shape

        if (numbersColor.isNotEmpty()) {
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
            btnBackSpace.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
        }

        if (actionsColor.isNotEmpty()) {
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


        if (acColor.isNotEmpty()) btnAc.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(acColor))
        if (equalColor.isNotEmpty()) btnEqual.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(equalColor))

        if (textColor.isNotEmpty()) setButtonsTextColor(textColor)

        if (shape.isNotEmpty()) {
            when (shape) {
                SHAPE_ROUNDED -> {
                    setButtonsRounded()
                }
                SHAPE_RECTANGLE -> {
                    setButtonsRectangle()
                }
                SHAPE_CIRCLE -> {
                    setButtonsCircle()
                }
            }

        }

    }

    private fun setButtonsTextColor(textColor: String) {
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

    private fun setButtonsCircle() {
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
        btnSquareRoot.background =
            ContextCompat.getDrawable(this, R.drawable.buttons_circle)
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

    private fun setButtonsRectangle() {
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
        btnSquareRoot.background =
            ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
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

    private fun setButtonsRounded() {
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
        btnSquareRoot.background =
            ContextCompat.getDrawable(this, R.drawable.buttons_round)
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

    @SuppressLint("DiscouragedPrivateApi")
    private fun initPopUpMenu() {
        val popupMenu2 = PopupMenu(this, btnMenu)
        popupMenu2.inflate(R.menu.menu_main)

        popupMenu2.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_to_history -> {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_to_about -> {
                    val cdd = CustomDialog(this)
                    val lp = WindowManager.LayoutParams()
                    lp.copyFrom(cdd.window?.attributes)
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                    cdd.show()
                    cdd.window?.attributes = lp
                    /* AlertDialog.Builder(this).setTitle(resources.getString(R.string.app_name)).setMessage(
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
                R.id.menu_to_custom_color -> {
                    val intent = Intent(this, CustomizationActivity::class.java)
                    intent.putExtra(MAIN_TEXT_SIZE, btnTextSize)
                    startActivity(intent)
                }
                R.id.menu_to_graph -> {
                    val intent = Intent(this, GraphActivity::class.java)
                    intent.putExtra(MAIN_TEXT_SIZE, btnTextSize)
                    startActivity(intent)
                }
            }
            false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu2.setForceShowIcon(true)
        } else {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu2)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: Exception) {

            }
        }


        btnMenu.setOnClickListener {
            popupMenu2.show()
        }
    }


    private fun getPrevTextSize() {
        edtResultTextSize = edtResult.textSize / resources.displayMetrics.scaledDensity
        edtPrevTextSize = edtPrevExp.textSize / resources.displayMetrics.scaledDensity
    }


    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, llMain).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

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

    private fun setTextSizes() {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val x = (mWidthPixels / dm.xdpi).toDouble().pow(2.0)
        val y = (mHeightPixels / dm.ydpi).toDouble().pow(2.0)
        val screenInches = sqrt(x + y)

        if (screenInches < 5.0) {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (mWidthPixels in 0..480) btnTextSize = 14 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 12 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 10 * resources.displayMetrics.density
            } else {
                if (mWidthPixels in 0..480) btnTextSize = 12 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 10 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 8 * resources.displayMetrics.density
            }
        }

        if (screenInches in 5.0..7.0) {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (mWidthPixels in 0..480) btnTextSize = 30 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 22 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 15 * resources.displayMetrics.density
                if (mWidthPixels in 1081..1600) btnTextSize = 13 * resources.displayMetrics.density
                if (mWidthPixels in 1601..2560) btnTextSize = 10 * resources.displayMetrics.density
            } else {
                if (mWidthPixels in 0..480) btnTextSize = 26 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 16 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 10 * resources.displayMetrics.density
                if (mWidthPixels in 1081..1600) btnTextSize = 8 * resources.displayMetrics.density
                if (mWidthPixels in 1601..2560) btnTextSize = 6 * resources.displayMetrics.density
            }
        }

        if (screenInches > 7.0) {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (mWidthPixels in 0..480) btnTextSize = 28 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 22 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 19 * resources.displayMetrics.density
                if (mWidthPixels in 1081..1600) btnTextSize = 16 * resources.displayMetrics.density
                if (mWidthPixels in 1601..2160) btnTextSize = 13 * resources.displayMetrics.density
                if (mWidthPixels in 2560..4000) btnTextSize = 10 * resources.displayMetrics.density
            } else {
                if (mWidthPixels in 0..480) btnTextSize = 24 * resources.displayMetrics.density
                if (mWidthPixels in 481..720) btnTextSize = 20 * resources.displayMetrics.density
                if (mWidthPixels in 721..1080) btnTextSize = 16 * resources.displayMetrics.density
                if (mWidthPixels in 1081..1600) btnTextSize = 13 * resources.displayMetrics.density
                if (mWidthPixels in 1601..2160) btnTextSize = 10 * resources.displayMetrics.density
                if (mWidthPixels in 2560..4000) btnTextSize = 8 * resources.displayMetrics.density
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
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            edtResult.textSize = btnTextSize * 13 / 8
            edtPrevExp.textSize = btnTextSize * 4 / 3
            edtTempResult.textSize = btnTextSize * 4 / 3
        } else {
            edtResult.textSize = btnTextSize * 5 / 2
            edtPrevExp.textSize = btnTextSize * 3 / 2
            edtTempResult.textSize = btnTextSize * 3 / 2
        }

    }

    private fun initViews() {
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
        edtResult = findViewById(R.id.edt_result)
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
        edtPrevExp = findViewById(R.id.tv_prev_expression)
        btnMenu = findViewById(R.id.btn_menu)
        edtTempResult = findViewById(R.id.edt_temp_result)

        llMain = findViewById(R.id.ll_main)
        llExpression = findViewById(R.id.ll_expression)
        cardView = findViewById(R.id.cardView)
        clExp = findViewById(R.id.cl_exp)
        llButtons = findViewById(R.id.ll_buttons)
    }

    private fun setOnClickListeners() {
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


    }


    private fun getRealDeviceSizeInPixels() {
        val windowManager: WindowManager = windowManager
        val display: Display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)


        // since SDK_INT = 1;
        mWidthPixels = displayMetrics.widthPixels
        mHeightPixels = displayMetrics.heightPixels

        // includes window decorations (statusBar bar/menu bar)
        try {
            mWidthPixels = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
            mHeightPixels = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
        } catch (ignored: java.lang.Exception) {
        }

        // includes window decorations (statusBar bar/menu bar)
        try {
            val realSize = Point()
            Display::class.java.getMethod("getRealSize", Point::class.java)
                .invoke(display, realSize)
            mWidthPixels = realSize.x
            mHeightPixels = realSize.y
        } catch (ignored: java.lang.Exception) {
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_zero -> {
                if (isPrevEqual) clear()
                updateEdtResult("0")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_one -> {
                if (isPrevEqual) clear()
                updateEdtResult("1")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_two -> {
                if (isPrevEqual) clear()
                updateEdtResult("2")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_three -> {
                if (isPrevEqual) clear()
                updateEdtResult("3")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_four -> {
                if (isPrevEqual) clear()
                updateEdtResult("4")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_five -> {
                if (isPrevEqual) clear()
                updateEdtResult("5")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_six -> {
                if (isPrevEqual) clear()
                updateEdtResult("6")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_seven -> {
                if (isPrevEqual) clear()
                updateEdtResult("7")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_eight -> {
                if (isPrevEqual) clear()
                updateEdtResult("8")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_nine -> {
                if (isPrevEqual) clear()
                updateEdtResult("9")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits++
            }

            R.id.btn_percent -> {
                if (checkForOperator()) {
                    backspaceFun()
                }
                updateEdtResult("%")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_division -> {
                if (checkForOperator()) {
                    backspaceFun()
                }
                updateEdtResult("÷")
                prevOperator = "÷"
                prevOperatorIndex = edtResult.text.lastIndex
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_multi -> {
                if (checkForOperator()) {
                    backspaceFun()
                }
                updateEdtResult("×")
                prevOperator = "×"
                prevOperatorIndex = edtResult.text.lastIndex
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_plus -> {
                if (checkForOperator()) {
                    backspaceFun()
                }
                updateEdtResult("+")
                prevOperator = "+"
                prevOperatorIndex = edtResult.text.lastIndex
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_minus -> {
                if (checkForOperator()) {
                    backspaceFun()
                }
                updateEdtResult("-")
                prevOperator = "-"
                prevOperatorIndex = edtResult.text.lastIndex
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_dot -> {
                if (!checkForDot()) updateEdtResult(".")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
            }


            R.id.btn_ac -> {
                clear()

                isPrevEqual = false
                prevOperator = ""
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_backspace -> {

                backspaceFun()
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE

            }

            R.id.btn_brackets -> {
                if (isPrevEqual) clear()
                addBrackets()
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_equal -> {
                if (edtPrevExp.text.toString() == "") {
                    updateEdtPrevExp(edtResult.text.toString())
                } else {
                    if (isPrevEqual && prevOperator != "") {
                        prevOperatorIndex = edtPrevExp.length() - 2
                        val tvText = edtPrevExp.text.toString().substring(prevOperatorIndex)
                        updateEdtPrevExp(tvText)
                        updateEdtResult(tvText)
                    } else {
                        if (checkForOperator()) {
                            val temp = edtResult.text.toString().substring(prevEqualIndex)
                            updateEdtPrevExp(temp)
                        }
                    }
                }

                calculateFun()
                currentNumDigits = 0
                edtPrevExp.visibility = View.VISIBLE
                isPrevEqual = true
            }

            R.id.btn_square_root -> {
                if (isPrevEqual) clear()
                updateEdtResult("√(")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_exp -> {
                if (checkForOperator()) {
                    backspaceFun()
                }
                updateEdtResult("^")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_pi -> {
                if (isPrevEqual) clear()
                if (edtResult.text.isNotEmpty()) {
                    if (edtResult.text[edtResult.text.toString().length - 1] == 'e'
                        || edtResult.text[edtResult.text.toString().length - 1] == 'π'
                    ) return
                }
                updateEdtResult("π")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_fact -> {
                if (checkForOperator()) {
                    backspaceFun()
                }
                updateEdtResult("!")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_more -> {
                isExpanded = !isExpanded
                expand()
                isPrevEqual = false

            }
            R.id.btn_sin -> {
                if (isPrevEqual) clear()
                updateEdtResult("sin(")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_cos -> {
                if (isPrevEqual) clear()
                updateEdtResult("cos(")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_tan -> {
                if (isPrevEqual) clear()
                updateEdtResult("tan(")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_e -> {
                if (isPrevEqual) clear()

                if (edtResult.text.isNotEmpty()) {
                    if (edtResult.text[edtResult.text.toString().length - 1] == 'e'
                        || edtResult.text[edtResult.text.toString().length - 1] == 'π'
                    ) return
                }
                updateEdtResult("e")
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_log2 -> {
                if (isPrevEqual) clear()
                updateEdtResult("log2(")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_log10 -> {
                if (isPrevEqual) clear()
                updateEdtResult("log10(")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_ln -> {
                if (isPrevEqual) clear()
                updateEdtResult("ln(")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

            R.id.btn_rad -> {
                if (isPrevEqual) clear()
                updateEdtResult("rad(")
                prevOperator = ""
                isPrevEqual = false
                isAvailableToCalculate()
                edtPrevExp.visibility = View.GONE
                currentNumDigits = 0
            }

        }
    }

    private fun clear() {
        edtResult.setText("")
        edtPrevExp.setText("")
        edtTempResult.setText("")
        edtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, edtResultTextSize)
        edtPrevExp.setTextSize(TypedValue.COMPLEX_UNIT_SP, edtPrevTextSize)
        edtTempResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, edtPrevTextSize)
    }


    private fun expand() {
        /*val transition = TransitionInflater.from(this).inflateTransition(R.transition.transition)*/
        if (isExpanded) {
            btnMore.text = getString(R.string.btn_more_expanded)
            /*TransitionManager.beginDelayedTransition(sinLinLay, transition)
            TransitionManager.beginDelayedTransition(logLinLay, transition)*/
            sinLinLay.visibility = View.VISIBLE
            logLinLay.visibility = View.VISIBLE
        } else {
            btnMore.text = getString(R.string.btn_more_not_expanded)
            /*TransitionManager.beginDelayedTransition(sinLinLay, transition)
            TransitionManager.beginDelayedTransition(logLinLay, transition)*/
            sinLinLay.visibility = View.GONE
            logLinLay.visibility = View.GONE
        }
    }

    private fun calculateFun() {
        edtTempResult.visibility = View.GONE
        checkForClosedBrackets()
        var userExp = edtResult.text.toString()
        val dbPrevExp = edtPrevExp.text.toString()
        val dbHelper = HistoryDatabaseHelper(this)

        userExp = userExp.replace("√", "sqrt")
        userExp = userExp.replace("÷", "/")
        userExp = userExp.replace("×", "*")
        userExp = userExp.replace("π", "pi")
        userExp = userExp.replace("%", "#")

        val expression = Expression(userExp)
        val value = expression.calculate()

        var result = value.toString()

        try {
            if (BigDecimal.valueOf(value).scale() > 8) {
                result = String.format("%.7f", value)
            }
        } catch (e: Exception) {

        }

        if (result[result.length - 1] == '0' && result[result.length - 2] == '.') {
            result = result.removeRange(result.length - 2, result.length)
        }
        edtResult.setText(result)
        edtResult.setSelection(result.length)
        prevEqualIndex = edtResult.text.lastIndex

        if (result != "Infinity" && result != "NaN") {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: String = sdf.format(Date())
            dbHelper.addOne(dbPrevExp, result, date)
        }
    }

    private fun checkForClosedBrackets() {
        val textLength = edtResult.text.length
        var openBracketCount = 0
        var closeBracketCount = 0
        for (i in 0 until textLength) {
            if (edtResult.text.toString().substring(i, i + 1) == "(") {
                openBracketCount++
            }
            if (edtResult.text.toString().substring(i, i + 1) == ")") {
                closeBracketCount++
            }
        }
        while (openBracketCount > closeBracketCount) {
            updateEdtResult(")")
            closeBracketCount++
        }
    }

    private fun checkForDot(): Boolean {
        for (element in edtResult.text.toString()) {
            if (element == '.') return true
        }
        return false
    }


    private fun checkForDot(s: String): Boolean {
        for (element in s) {
            if (element == '.') return true
        }
        return false
    }


    private fun checkForOperator(): Boolean {
        val cursor = edtResult.selectionStart
        if (cursor == 0) {
            return false
        }
        var needToChange = false
        when (edtResult.text.toString()[cursor - 1]) {
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

    private fun checkForOperator(c: Char): Boolean {
        when (c) {
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

    private fun addBrackets() {
        val cursorPos = edtResult.selectionStart
        val textLength = edtResult.text.length
        var openBracketCount = 0
        var closeBracketCount = 0

        if (textLength == 0) {
            updateEdtResult("(")
            edtResult.setSelection(cursorPos + 1)
            return
        }

        if (edtResult.text.toString()[cursorPos - 1] == '+' || edtResult.text.toString()[cursorPos - 1] == '-' ||
            edtResult.text.toString()[cursorPos - 1] == '×' || edtResult.text.toString()[cursorPos - 1] == '÷' ||
            edtResult.text.toString()[cursorPos - 1] == '^' || edtResult.text.toString()[cursorPos - 1] == '!' ||
            edtResult.text.toString()[cursorPos - 1] == '√'
        ) {
            updateEdtResult("(")
            edtResult.setSelection(cursorPos + 1)
            return
        }

        for (i in 0 until cursorPos) {
            if (edtResult.text.toString().substring(i, i + 1) == "(") {
                openBracketCount++
            }
            if (edtResult.text.toString().substring(i, i + 1) == ")") {
                closeBracketCount++
            }
        }

        if (openBracketCount == closeBracketCount || edtResult.text.toString()
                .substring(textLength - 1, textLength) == "("
        ) {
            if (updateEdtResult("(")) edtResult.setSelection(cursorPos + 1)
        } else if (closeBracketCount < openBracketCount && edtResult.text.toString()
                .substring(textLength - 1, textLength) != "("
        ) {
            if (updateEdtResult(")")) edtResult.setSelection(cursorPos + 1)
        }

    }

    private fun updateEdtResult(strToAdd: String): Boolean {

        if (strToAdd.length == 1 && !checkForOperator(
                strToAdd[0]
            ) && currentNumDigits >= MAX_DIGITS
        ) {
            return false
        }

        var textSize = edtResult.textSize / resources.displayMetrics.scaledDensity

        Log.i(
            TAG,
            "updateEdtResult: ${(edtResult.text.length + strToAdd.length) * edtResult.textSize}"
        )

        if ((edtResult.text.length + strToAdd.length) * textSize > cardView.width && textSize > 22) {
            for (i in 0..strToAdd.length) {
                textSize -= if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2
                else 1
                edtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                if (textSize < 22) {
                    break
                }
            }
        } else {
            if ((edtResult.text.length) * textSize > cardView.width) return false
        }

        var prevString = edtResult.text.toString()

        if (prevString == "NaN" || prevString == "Infinity") {
            prevString = ""
            clear()
        }

        val cursorPos = edtResult.selectionStart
        val leftString = prevString.substring(0, cursorPos)
        val rightString = prevString.substring(cursorPos)
        edtResult.setText(String.format("%s%s%s", leftString, strToAdd, rightString))
        edtResult.setSelection(cursorPos + strToAdd.length)

        return true
        /*var textSize = edtResult.textSize / resources.displayMetrics.scaledDensity

        if (edtResult.width + textSize > mWidthPixels)
        {
            textSize -= 3
            edtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }*/

        /*var textSize = edtResult.textSize / resources.displayMetrics.scaledDensity

        if (edtResult.length() > 8 && textSize > edtResultTextSize / 3 * 2 &&
            resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
        )
        {
            textSize -= 3
            edtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }

        if (edtResult.length() > 30 && textSize > edtResultTextSize / 4 * 3 &&
            resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
        )
        {
            textSize -= 2
            edtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }*/

    }

    private fun updateEdtPrevExp(S: String) {

        var textSize = edtPrevExp.textSize / resources.displayMetrics.scaledDensity

        Log.i(TAG, "updateEdtResult: ${(edtPrevExp.text.length + S.length) * edtPrevExp.textSize}")

        if ((edtPrevExp.text.length + S.length) * textSize > cardView.width) {
            for (i in 0..S.length) {
                textSize -= 2
                edtPrevExp.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            }
        }

        var prevStr = edtPrevExp.text.toString()
        prevStr += S
        edtPrevExp.setText(prevStr)
        /*var textSize = edtPrevExp.textSize / resources.displayMetrics.scaledDensity

        if (edtPrevExp.width + textSize > mWidthPixels)
        {
            textSize -= 3
            edtPrevExp.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }*/
    }


    private fun backspaceFun() {
        if (edtResult.length() == 0) {
            return
        }

        var cursorPos = edtResult.selectionStart
        val textLength = edtResult.text.length

        val lastChar = edtResult.text.toString()[textLength - 1]

        if (edtResult.length() >= 3) {
            val a = edtResult.text.toString().substring(edtResult.text.lastIndex - 2)
            if (a == "ln(") {
                edtResult.text.replace(edtResult.text.lastIndex - 2, edtResult.text.length, "")
                edtResult.setSelection(cursorPos - 3)
                cursorPos -= 3
            }

            if (edtResult.length() >= 4) {
                val b = edtResult.text.toString().substring(edtResult.text.lastIndex - 3)
                if (b == "sin(" || b == "cos(" || b == "tan(" || b == "rad(") {
                    edtResult.text.replace(edtResult.text.lastIndex - 3, edtResult.text.length, "")
                    edtResult.setSelection(cursorPos - 4)
                    cursorPos -= 4
                }
            }

            if (textLength >= 5) {
                val b = edtResult.text.toString().substring(edtResult.text.lastIndex - 4)
                if ("log2(" in b) {
                    edtResult.text.replace(edtResult.text.lastIndex - 4, edtResult.text.length, "")
                    edtResult.setSelection(cursorPos - 5)
                    cursorPos -= 5
                }
            }

            if (textLength >= 6) {
                val c = edtResult.text.toString().substring(edtResult.text.lastIndex - 5)
                if ("log10(" in c) {
                    edtResult.text.replace(edtResult.text.lastIndex - 5, edtResult.text.length, "")
                    edtResult.setSelection(cursorPos - 6)
                    cursorPos -= 6
                }
            }


        }

        /*var textSize = edtResult.textSize / resources.displayMetrics.scaledDensity*/



        if (cursorPos != 0 && textLength != 0) {
            val selection: SpannableStringBuilder =
                edtResult.text as SpannableStringBuilder
            selection.replace(cursorPos - 1, cursorPos, "")
            if (!checkForOperator(lastChar)) {
                currentNumDigits--
            }
            edtResult.text = selection
            edtResult.setSelection(cursorPos - 1)
        }

        /*if (edtResult.length() <= 5 && textSize < edtResultTextSize &&
            resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
        )
        {
            textSize += 5
            edtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }

        if (edtResult.length() > 18 && textSize < edtResultTextSize &&
            resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
        )
        {
            textSize += 2
            edtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }*/

        isAvailableToCalculate()
        edtPrevExp.visibility = View.GONE
    }


    private fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}

