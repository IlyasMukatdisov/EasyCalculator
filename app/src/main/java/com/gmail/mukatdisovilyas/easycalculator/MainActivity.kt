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

    private lateinit var darkGrayColor: ColorStateList
    private lateinit var semiGrayColor: ColorStateList
    private lateinit var lightGrayColor: ColorStateList
    private lateinit var alphaColor: ColorStateList
    private lateinit var whiteColor: ColorStateList
    private lateinit var blackColor: ColorStateList

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
            if (isDarkThemeOn())
            {

                btnZero.backgroundTintList = darkGrayColor
                btnOne.backgroundTintList = darkGrayColor
                btnTwo.backgroundTintList = darkGrayColor
                btnThree.backgroundTintList = darkGrayColor
                btnFour.backgroundTintList = darkGrayColor
                btnFive.backgroundTintList = darkGrayColor
                btnSix.backgroundTintList = darkGrayColor
                btnSeven.backgroundTintList = darkGrayColor
                btnEight.backgroundTintList = darkGrayColor
                btnNine.backgroundTintList = darkGrayColor
                btnDot.backgroundTintList = darkGrayColor
                btnBackSpace.backgroundTintList = darkGrayColor
                edtMain.backgroundTintList = darkGrayColor
                edtSecond.backgroundTintList = darkGrayColor

                btnZero.setTextColor(whiteColor)
                btnOne.setTextColor(whiteColor)
                btnTwo.setTextColor(whiteColor)
                btnThree.setTextColor(whiteColor)
                btnFour.setTextColor(whiteColor)
                btnFive.setTextColor(whiteColor)
                btnSix.setTextColor(whiteColor)
                btnSeven.setTextColor(whiteColor)
                btnEight.setTextColor(whiteColor)
                btnNine.setTextColor(whiteColor)
                btnDot.setTextColor(whiteColor)
                btnBackSpace.setTextColor(whiteColor)


            }
        }

        if (isDarkThemeOn())
        {
            edtMain.backgroundTintList = darkGrayColor
            edtSecond.backgroundTintList = darkGrayColor
            llMain.backgroundTintList = blackColor
            llExpression.backgroundTintList = blackColor
            cardView.backgroundTintList = darkGrayColor
            clExp.backgroundTintList = darkGrayColor
            llButtons.backgroundTintList = blackColor
            btnMenu.backgroundTintList = darkGrayColor
            btnMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_more_vert_white))

            edtMain.setTextColor(whiteColor)
            edtSecond.setTextColor(whiteColor)

        }

        edtMain.showSoftInputOnFocus = false
        edtSecond.showSoftInputOnFocus = false

        /*if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        }*/

        getPrevTextSize()
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

            val nColor = ColorStateList.valueOf(Color.parseColor(numbersColor))

            btnZero.backgroundTintList = nColor
            btnOne.backgroundTintList = nColor
            btnTwo.backgroundTintList = nColor
            btnThree.backgroundTintList = nColor
            btnFour.backgroundTintList = nColor
            btnFive.backgroundTintList = nColor
            btnSix.backgroundTintList = nColor
            btnSeven.backgroundTintList = nColor
            btnEight.backgroundTintList = nColor
            btnNine.backgroundTintList = nColor
            btnDot.backgroundTintList = nColor
            btnBackSpace.backgroundTintList = nColor
        }

        if (actionsColor.isNotEmpty())
        {
            val actColor = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnPlus.backgroundTintList = actColor
            btnMinus.backgroundTintList = actColor
            btnMulti.backgroundTintList = actColor
            btnDivision.backgroundTintList = actColor
            btnPercent.backgroundTintList = actColor
            btnExp.backgroundTintList = actColor
            btnPi.backgroundTintList = actColor
            btnFact.backgroundTintList = actColor
            btnSquareRoot.backgroundTintList = actColor
            btnBrackets.backgroundTintList = actColor
            btnMore.backgroundTintList = actColor
            btnSin.backgroundTintList = actColor
            btnCos.backgroundTintList = actColor
            btnTan.backgroundTintList = actColor
            btnE.backgroundTintList = actColor
            btnLg.backgroundTintList = actColor
            btnLn.backgroundTintList = actColor
            btnLog2.backgroundTintList = actColor
            btnRad.backgroundTintList = actColor
        }


        if (acColor.isNotEmpty()) btnAc.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(acColor))
        if (equalColor.isNotEmpty()) btnEqual.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(equalColor))

        if (textColor.isNotEmpty()) setButtonsTextColor(textColor)

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

    }

    private fun setButtonsTextColor(textColor: String)
    {
        val tColor = ColorStateList.valueOf(Color.parseColor(textColor))
        btnZero.setTextColor(tColor)
        btnOne.setTextColor(tColor)
        btnTwo.setTextColor(tColor)
        btnThree.setTextColor(tColor)
        btnFour.setTextColor(tColor)
        btnFive.setTextColor(tColor)
        btnSix.setTextColor(tColor)
        btnSeven.setTextColor(tColor)
        btnEight.setTextColor(tColor)
        btnNine.setTextColor(tColor)
        btnDot.setTextColor(tColor)
        btnBackSpace.setTextColor(tColor)

        btnPlus.setTextColor(tColor)
        btnMinus.setTextColor(tColor)
        btnMulti.setTextColor(tColor)
        btnDivision.setTextColor(tColor)
        btnPercent.setTextColor(tColor)
        btnExp.setTextColor(tColor)
        btnPi.setTextColor(tColor)
        btnFact.setTextColor(tColor)
        btnSquareRoot.setTextColor(tColor)
        btnBrackets.setTextColor(tColor)
        btnMore.setTextColor(tColor)
        btnSin.setTextColor(tColor)
        btnCos.setTextColor(tColor)
        btnTan.setTextColor(tColor)
        btnE.setTextColor(tColor)
        btnLg.setTextColor(tColor)
        btnLn.setTextColor(tColor)
        btnLog2.setTextColor(tColor)
        btnRad.setTextColor(tColor)

        btnAc.setTextColor(tColor)
        btnEqual.setTextColor(tColor)

    }

    private fun setButtonsCircle()
    {
        val circle = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnZero.background = circle
        btnOne.background = circle
        btnTwo.background = circle
        btnThree.background = circle
        btnFour.background = circle
        btnFive.background = circle
        btnSix.background = circle
        btnSeven.background = circle
        btnEight.background = circle
        btnNine.background = circle
        btnDot.background = circle
        btnBackSpace.background = circle

        btnPlus.background = circle
        btnMinus.background = circle
        btnMulti.background = circle
        btnDivision.background = circle
        btnPercent.background = circle
        btnExp.background = circle
        btnPi.background = circle
        btnFact.background = circle
        btnSquareRoot.background = circle
        btnBrackets.background = circle
        btnMore.background = circle
        btnSin.background = circle
        btnCos.background = circle
        btnTan.background = circle
        btnE.background = circle
        btnLg.background = circle
        btnLn.background = circle
        btnLog2.background = circle
        btnRad.background = circle
        btnAc.background = circle
        btnEqual.background = circle
    }

    private fun setButtonsRectangle()
    {
        val rectangle = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnZero.background = rectangle
        btnOne.background = rectangle
        btnTwo.background = rectangle
        btnThree.background = rectangle
        btnFour.background = rectangle
        btnFive.background = rectangle
        btnSix.background = rectangle
        btnSeven.background = rectangle
        btnEight.background = rectangle
        btnNine.background = rectangle
        btnDot.background = rectangle
        btnBackSpace.background = rectangle

        btnPlus.background = rectangle
        btnMinus.background = rectangle
        btnMulti.background = rectangle
        btnDivision.background = rectangle
        btnPercent.background = rectangle
        btnExp.background = rectangle
        btnPi.background = rectangle
        btnFact.background = rectangle
        btnSquareRoot.background = rectangle
        btnBrackets.background = rectangle
        btnMore.background = rectangle
        btnSin.background = rectangle
        btnCos.background = rectangle
        btnTan.background = rectangle
        btnE.background = rectangle
        btnLg.background = rectangle
        btnLn.background = rectangle
        btnLog2.background = rectangle
        btnRad.background = rectangle
        btnAc.background = rectangle
        btnEqual.background = rectangle
    }

    private fun setButtonsRounded()
    {
        val rounded = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnZero.background = rounded
        btnOne.background = rounded
        btnTwo.background = rounded
        btnThree.background = rounded
        btnFour.background = rounded
        btnFive.background = rounded
        btnSix.background = rounded
        btnSeven.background = rounded
        btnEight.background = rounded
        btnNine.background = rounded
        btnDot.background = rounded
        btnBackSpace.background = rounded

        btnPlus.background = rounded
        btnMinus.background = rounded
        btnMulti.background = rounded
        btnDivision.background = rounded
        btnPercent.background = rounded
        btnExp.background = rounded
        btnPi.background = rounded
        btnFact.background = rounded
        btnSquareRoot.background = rounded
        btnBrackets.background = rounded
        btnMore.background = rounded
        btnSin.background = rounded
        btnCos.background = rounded
        btnTan.background = rounded
        btnE.background = rounded
        btnLg.background = rounded
        btnLn.background = rounded
        btnLog2.background = rounded
        btnRad.background = rounded
        btnAc.background = rounded
        btnEqual.background = rounded

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
                        if (isDarkThemeOn()) btnMenu.backgroundTintList = alphaColor
                        else btnMenu.backgroundTintList = semiGrayColor
                    }
                    MotionEvent.ACTION_UP   ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList = darkGrayColor
                        else btnMenu.backgroundTintList = lightGrayColor
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
                        if (isDarkThemeOn()) btnMenu.backgroundTintList = alphaColor
                        else btnMenu.backgroundTintList = semiGrayColor
                    }
                    MotionEvent.ACTION_UP   ->
                    {
                        if (isDarkThemeOn()) btnMenu.backgroundTintList = darkGrayColor
                        else btnMenu.backgroundTintList = lightGrayColor
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
        darkGrayColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        semiGrayColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.semi_gray))
        lightGrayColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
        alphaColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.alpha))
        whiteColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        blackColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))

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
        var i : Int = if(cursor==0) cursor
        else cursor-1
        var number = ""
        while (i >= 0)
        {
            if (!(checkForDigit(edtMain.text[i]) || edtMain.text[i] == '.')) break
            number = "${edtMain.text[i]}$number"
            i--
        }
        i = if(cursor==edtMain.text.lastIndex) cursor-1
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

        return true/*var textSize = edtResult.textSize / resources.displayMetrics.scaledDensity

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


    /*private fun updateEdtTempResult(S: String)
    {

        var textSize = edtTempResult.textSize / resources.displayMetrics.scaledDensity

        Log.i(TAG,
            "updateEdtPrevExp: ${(edtTempResult.text.length + S.length) * edtTempResult.textSize}")

        if (edtTempResult.width > cardView.width)
        {
            for (i in 0..S.length)
            {
                textSize -= 2
                edtTempResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            }
        }

        var prevStr = edtTempResult.text.toString()
        prevStr += S
        edtTempResult.setText(prevStr)*//*var textSize = edtPrevExp.textSize / resources.displayMetrics.scaledDensity

        if (edtPrevExp.width + textSize > mWidthPixels)
        {
            textSize -= 3
            edtPrevExp.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }*//*
    }*/


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
        if (edtMain.length() == 0 || edtMain.selectionStart == 0 || digitLimit()>= MAX_DIGITS)
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

