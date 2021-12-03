package com.gmail.mukatdisovilyas.easycalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.transition.TransitionManager
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.gmail.mukatdisovilyas.easycalculator.utils.*
import com.google.android.material.snackbar.Snackbar
import org.mariuszgromada.math.mxparser.Function


class GraphActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener
{

    companion object
    {
        private const val TAG = "GraphActivity"
    }


    private var doubleBackToExitPressedOnce = false

    private var isExpanded = false

    private lateinit var llMenu: LinearLayout
    private lateinit var ccGraph: ConstraintLayout

    private lateinit var linearLayoutButtons: LinearLayout
    private lateinit var llParent: LinearLayout
    private lateinit var llKeyboard: LinearLayout

    private lateinit var tvFx: TextView
    private lateinit var tvXValues: TextView
    private lateinit var tvSemicolon: TextView
    private lateinit var tvXStep: TextView

    private lateinit var btnMenu: ImageButton

    private lateinit var edtExp: EditText
    private lateinit var edtXStart: EditText
    private lateinit var edtXEnd: EditText
    private lateinit var edtXStep: EditText

    private lateinit var btnZeroKeyboard: Button
    private lateinit var btnOneKeyboard: Button
    private lateinit var btnTwoKeyboard: Button
    private lateinit var btnThreeKeyboard: Button
    private lateinit var btnFourKeyboard: Button
    private lateinit var btnFiveKeyboard: Button
    private lateinit var btnSixKeyboard: Button
    private lateinit var btnSevenKeyboard: Button
    private lateinit var btnEightKeyboard: Button
    private lateinit var btnNineKeyboard: Button
    private lateinit var btnDotKeyboard: Button
    private lateinit var btnBackspaceKeyboard: Button

    private lateinit var btnX: Button
    private lateinit var btnBuild: Button
    private lateinit var chart: LineChart
    private lateinit var btnBrackets: Button
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
    private lateinit var btnSquareRoot: Button
    private lateinit var btnExp: Button
    private lateinit var btnPi: Button
    private lateinit var btnSin: Button
    private lateinit var btnCos: Button
    private lateinit var btnTan: Button
    private lateinit var btnE: Button
    private lateinit var btnLg: Button
    private lateinit var btnLog2: Button
    private lateinit var btnLn: Button

    private var currentNumDigits = 0

    private var viewsTextSize: Float = 0f

    private var buildClickCount: Int = 0

    private val dataSets: MutableList<ILineDataSet> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)


        initViews()
        hideSystemKeyboard()
        setOnClickListeners()
        initPopUpMenu()


        if (!isEmptyCustomColor()) customization()
        else if (isDarkThemeOn()) setDefaultTheme()

        if (intent.hasExtra(MAIN_TEXT_SIZE))
        {
            viewsTextSize = intent.getFloatExtra(MAIN_TEXT_SIZE, 0f) / 6 * 5
            setTextSizes()
        }

        buildGraph("cos(x)")

        val marker: IMarker = MyMarkerView(this, R.layout.chart_marker)
        chart.marker = marker

        if (isDarkThemeOn()) setDarkChartMenu()
        chart.legend.textSize = 18f


    }


    override fun onBackPressed()
    {
        if (isExpanded)
        {
            isExpanded = false
            TransitionManager.beginDelayedTransition(llParent)
            linearLayoutButtons.visibility = View.GONE
            llKeyboard.visibility = View.GONE
        }
        else
        {
            if (doubleBackToExitPressedOnce) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
    }


    private fun setDarkChartMenu()
    {
        chart.axisLeft.textColor = Color.WHITE
        chart.axisLeft.axisLineColor = Color.WHITE
        chart.axisLeft.gridColor = Color.WHITE

        chart.axisRight.textColor = Color.WHITE
        chart.axisRight.axisLineColor = Color.WHITE
        chart.axisRight.gridColor = Color.WHITE

        chart.xAxis.textColor = Color.WHITE
        chart.xAxis.axisLineColor = Color.WHITE
        chart.xAxis.gridColor = Color.WHITE
        chart.legend.textColor = Color.WHITE

        btnMenu.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
        btnMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_more_vert_white))
    }

    private fun setDefaultTheme()
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


        edtExp.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))

        btnZero.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnOne.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnTwo.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnThree.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnFour.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnFive.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnSix.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnSeven.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnEight.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnNine.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnDot.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnBackSpace.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))



        btnZeroKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnOneKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnTwoKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnThreeKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnFourKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnFiveKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnSixKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnSevenKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnEightKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnNineKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnDotKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        btnBackspaceKeyboard.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))


        btnZeroKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnOneKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnTwoKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnThreeKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnFourKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnFiveKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnSixKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnSevenKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnEightKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnNineKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnDotKeyboard.setTextColor(
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        btnBackspaceKeyboard.setTextColor(
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
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.semi_gray))
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
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.semi_gray))
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


    private fun hideSystemKeyboard()
    {
        edtExp.showSoftInputOnFocus = false
        edtXStart.showSoftInputOnFocus = false
        edtXEnd.showSoftInputOnFocus = false
        edtXStep.showSoftInputOnFocus = false
    }


    private fun isEmptyCustomColor(): Boolean
    {
        val dbHelper = CustomModelDatabaseHelper(this)
        return dbHelper.isCustomizationEmpty()
    }


    private fun setTextSizes()
    {
        btnZero.textSize = viewsTextSize
        btnOne.textSize = viewsTextSize
        btnTwo.textSize = viewsTextSize
        btnThree.textSize = viewsTextSize
        btnFour.textSize = viewsTextSize
        btnFive.textSize = viewsTextSize
        btnSix.textSize = viewsTextSize
        btnSeven.textSize = viewsTextSize
        btnEight.textSize = viewsTextSize
        btnNine.textSize = viewsTextSize
        btnPlus.textSize = viewsTextSize
        btnMinus.textSize = viewsTextSize
        btnMulti.textSize = viewsTextSize
        btnDivision.textSize = viewsTextSize
        btnDot.textSize = viewsTextSize
        btnBrackets.textSize = viewsTextSize
        btnBackSpace.textSize = viewsTextSize
        btnSquareRoot.textSize = viewsTextSize
        btnExp.textSize = viewsTextSize
        btnPi.textSize = viewsTextSize
        btnSin.textSize = viewsTextSize
        btnCos.textSize = viewsTextSize
        btnTan.textSize = viewsTextSize
        btnE.textSize = viewsTextSize
        btnLog2.textSize = viewsTextSize
        btnLg.textSize = viewsTextSize
        btnLn.textSize = viewsTextSize
        btnX.textSize = viewsTextSize
        btnZeroKeyboard.textSize = viewsTextSize * 3 / 2
        btnOneKeyboard.textSize = viewsTextSize * 3 / 2
        btnTwoKeyboard.textSize = viewsTextSize * 3 / 2
        btnThreeKeyboard.textSize = viewsTextSize * 3 / 2
        btnFourKeyboard.textSize = viewsTextSize * 3 / 2
        btnFiveKeyboard.textSize = viewsTextSize * 3 / 2
        btnSixKeyboard.textSize = viewsTextSize * 3 / 2
        btnSevenKeyboard.textSize = viewsTextSize * 3 / 2
        btnEightKeyboard.textSize = viewsTextSize * 3 / 2
        btnNineKeyboard.textSize = viewsTextSize * 3 / 2
        btnDotKeyboard.textSize = viewsTextSize * 3 / 2
        btnBackspaceKeyboard.textSize = viewsTextSize * 3 / 2

        btnBuild.textSize=viewsTextSize


        tvFx.textSize = viewsTextSize
        tvXValues.textSize = viewsTextSize
        tvSemicolon.textSize = viewsTextSize
        tvXStep.textSize = viewsTextSize

        edtExp.textSize = viewsTextSize
        edtXStart.textSize = viewsTextSize
        edtXEnd.textSize = viewsTextSize
        edtXStep.textSize = viewsTextSize


    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initViews()
    {
        ccGraph = findViewById(R.id.cc_content)

        llMenu = findViewById(R.id.ll_menu)
        linearLayoutButtons = findViewById(R.id.ll_buttons)
        llKeyboard = findViewById(R.id.ll_keyboard)

        btnMenu = findViewById(R.id.btn_menu)

        edtExp = findViewById(R.id.edt_graph)
        edtXStart = findViewById(R.id.edt_x_begin)
        edtXEnd = findViewById(R.id.edt_x_end)
        edtXStep = findViewById(R.id.edt_x_step)


        btnBuild = findViewById(R.id.btn_graph_build)
        chart = findViewById(R.id.line_chart)
        chart.description.isEnabled = false


        btnBrackets = findViewById(R.id.btn_brackets)
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
        btnSquareRoot = findViewById(R.id.btn_square_root)
        btnExp = findViewById(R.id.btn_exp)
        btnPi = findViewById(R.id.btn_pi)
        btnSin = findViewById(R.id.btn_sin)
        btnCos = findViewById(R.id.btn_cos)
        btnTan = findViewById(R.id.btn_tan)
        btnE = findViewById(R.id.btn_e)
        btnLog2 = findViewById(R.id.btn_log2)
        btnLg = findViewById(R.id.btn_log10)
        btnLn = findViewById(R.id.btn_ln)
        btnX = findViewById(R.id.btn_x)

        btnZeroKeyboard = findViewById(R.id.btn_zero_keyboard)
        btnOneKeyboard = findViewById(R.id.btn_one_keyboard)
        btnTwoKeyboard = findViewById(R.id.btn_two_keyboard)
        btnThreeKeyboard = findViewById(R.id.btn_three_keyboard)
        btnFourKeyboard = findViewById(R.id.btn_four_keyboard)
        btnFiveKeyboard = findViewById(R.id.btn_five_keyboard)
        btnSixKeyboard = findViewById(R.id.btn_six_keyboard)
        btnSevenKeyboard = findViewById(R.id.btn_seven_keyboard)
        btnEightKeyboard = findViewById(R.id.btn_eight_keyboard)
        btnNineKeyboard = findViewById(R.id.btn_nine_keyboard)
        btnDotKeyboard = findViewById(R.id.btn_dot_minus_keyboard)
        btnBackspaceKeyboard = findViewById(R.id.btn_backspace_keyboard)


        tvFx = findViewById(R.id.tv_fx)
        tvXValues = findViewById(R.id.tv_x_values)
        tvSemicolon = findViewById(R.id.tv_semicolon)
        tvXStep = findViewById(R.id.tv_x_step)

        edtExp.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                isExpanded = true
                TransitionManager.beginDelayedTransition(llParent)
                linearLayoutButtons.visibility = View.VISIBLE
                llKeyboard.visibility = View.GONE
            }
            else
            {
                isExpanded = false
                TransitionManager.beginDelayedTransition(llParent)
                linearLayoutButtons.visibility = View.GONE
                llKeyboard.visibility = View.GONE
            }
        }

        edtXStart.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                isExpanded = true
                TransitionManager.beginDelayedTransition(llParent)
                llKeyboard.visibility = View.VISIBLE
                linearLayoutButtons.visibility = View.GONE
            }
            else
            {
                isExpanded = false
                TransitionManager.beginDelayedTransition(llParent)
                llKeyboard.visibility = View.GONE
                linearLayoutButtons.visibility = View.GONE
            }
        }
        edtXEnd.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                isExpanded = true
                TransitionManager.beginDelayedTransition(llParent)
                llKeyboard.visibility = View.VISIBLE
                linearLayoutButtons.visibility = View.GONE
            }
            else
            {
                isExpanded = false
                TransitionManager.beginDelayedTransition(llParent)
                llKeyboard.visibility = View.GONE
                linearLayoutButtons.visibility = View.GONE
            }
        }
        edtXStep.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                isExpanded = true
                TransitionManager.beginDelayedTransition(llParent)
                llKeyboard.visibility = View.VISIBLE
                linearLayoutButtons.visibility = View.GONE
            }
            else
            {
                isExpanded = false
                TransitionManager.beginDelayedTransition(llParent)
                llKeyboard.visibility = View.GONE
                linearLayoutButtons.visibility = View.GONE
            }
        }

        isExpanded = llKeyboard.visibility != View.GONE
        llMenu.setOnTouchListener(this)
        btnMenu.setOnTouchListener(this)
        llParent = findViewById(R.id.ll_parent)

        /* edtExp.filters = arrayOf<InputFilter>(LengthFilter(15))
         edtXStart.filters = arrayOf<InputFilter>(LengthFilter(5))
         edtXEnd.filters = arrayOf<InputFilter>(LengthFilter(5))
         edtXStep.filters = arrayOf<InputFilter>(LengthFilter(5))*/
    }

    private fun setOnClickListeners()
    {
        btnBuild.setOnClickListener(this)
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
        btnDivision.setOnClickListener(this)
        btnMulti.setOnClickListener(this)
        btnPlus.setOnClickListener(this)
        btnMinus.setOnClickListener(this)
        btnDot.setOnClickListener(this)
        btnBrackets.setOnClickListener(this)
        btnBackSpace.setOnClickListener(this)
        btnSquareRoot.setOnClickListener(this)
        btnExp.setOnClickListener(this)
        btnPi.setOnClickListener(this)
        btnSin.setOnClickListener(this)
        btnCos.setOnClickListener(this)
        btnTan.setOnClickListener(this)
        btnE.setOnClickListener(this)
        btnLog2.setOnClickListener(this)
        btnLg.setOnClickListener(this)
        btnLn.setOnClickListener(this)
        btnX.setOnClickListener(this)
        btnZeroKeyboard.setOnClickListener(this)
        btnOneKeyboard.setOnClickListener(this)
        btnTwoKeyboard.setOnClickListener(this)
        btnThreeKeyboard.setOnClickListener(this)
        btnFourKeyboard.setOnClickListener(this)
        btnFiveKeyboard.setOnClickListener(this)
        btnSixKeyboard.setOnClickListener(this)
        btnSevenKeyboard.setOnClickListener(this)
        btnEightKeyboard.setOnClickListener(this)
        btnNineKeyboard.setOnClickListener(this)
        btnDotKeyboard.setOnClickListener(this)
        btnBackspaceKeyboard.setOnClickListener(this)

        edtExp.setOnClickListener(this)
        edtXStart.setOnClickListener(this)
        edtXEnd.setOnClickListener(this)
        edtXStep.setOnClickListener(this)

    }


    private fun buildGraph(exp: String): Boolean
    {
        var f = "f(x)=$exp"

        f = f.replace("√", "sqrt")
        f = f.replace("÷", "/")
        f = f.replace("×", "*")
        f = f.replace("π", "pi")


        val function = Function(f)

        if (!function.checkSyntax())
        {
            Snackbar.make(llParent, "Please check the syntax of function!", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }


        val yValues: MutableList<Entry> = ArrayList()

        var xStart: Double
        var xEnd: Double
        var xStep: Double
        try
        {
            xStart = edtXStart.text.toString().toDouble()
            xEnd = edtXEnd.text.toString().toDouble()
            xStep = edtXStep.text.toString().toDouble()
        }
        catch (e: Exception)
        {
            xStart = -10.0
            xEnd = 10.0
            xStep = 0.1
        }

        if (xStep <= 0)
        {
            Snackbar.make(llParent, "Step must be positive!", Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (xStart >= xEnd)
        {
            Snackbar.make(llParent, "End value must be bigger than Start value!",
                Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (("log" in exp || "ln" in exp) && xStart <= 0.0 && xStep > 0.0)
        {
            xStart = xStep
        }

        var y: Double
        while (xStart <= xEnd)
        {
            if (("log" in exp || "ln" in exp) && xStart == 1.0)
            {
                xStart++
                continue
            }
            y = function.calculate(xStart)
            yValues.add(Entry(xStart.toFloat(), y.toFloat()))
            xStart += xStep
        }


        val set = LineDataSet(yValues, f)
        set.setDrawValues(false)
        val rnd = java.util.Random()

        val color: Int = if (isDarkThemeOn())
        {
            Color.argb(255, 100 + rnd.nextInt(156), 100 + rnd.nextInt(156), 100 + rnd.nextInt(156))
        }
        else
        {
            Color.argb(255, rnd.nextInt(156), rnd.nextInt(156), rnd.nextInt(156))
        }
        set.color = color
        set.setDrawCircles(false)
        set.lineWidth = 2f
        set.setCircleColor(color)

        dataSets.add(set)

        val lineData = LineData(dataSets)

        chart.data = lineData
        chart.legend.isEnabled = true
        chart.invalidate()
        return true
    }

    /*  private fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int)
      {
          if (v.layoutParams is MarginLayoutParams)
          {
              val p = v.layoutParams as MarginLayoutParams
              p.setMargins(l, t, r, b)
              v.requestLayout()
          }
      }
  */

    private fun Context.isDarkThemeOn(): Boolean
    {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onClick(v: View?)
    {
        when (v?.id)
        {

            R.id.edt_graph              ->
            {
                isExpanded = true
                linearLayoutButtons.visibility = View.VISIBLE
                llKeyboard.visibility = View.GONE
            }

            R.id.edt_x_begin            ->
            {
                isExpanded = true
                linearLayoutButtons.visibility = View.GONE
                llKeyboard.visibility = View.VISIBLE
            }

            R.id.edt_x_end              ->
            {
                isExpanded = true
                linearLayoutButtons.visibility = View.GONE
                llKeyboard.visibility = View.VISIBLE
            }

            R.id.edt_x_step             ->
            {
                isExpanded = true
                linearLayoutButtons.visibility = View.GONE
                llKeyboard.visibility = View.VISIBLE
            }

            R.id.btn_zero_keyboard      ->
            {

                if (edtXStart.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXStart, "0")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "0")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "0")
                }
            }

            R.id.btn_one_keyboard       ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    updateEdt(edtXStart, "1")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "1")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "1")
                }
            }

            R.id.btn_two_keyboard       ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    updateEdt(edtXStart, "2")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "2")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "2")
                }
            }

            R.id.btn_three_keyboard     ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    updateEdt(edtXStart, "3")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "3")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "3")
                }
            }

            R.id.btn_four_keyboard      ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    updateEdt(edtXStart, "4")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "4")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "4")
                }
            }

            R.id.btn_five_keyboard      ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    updateEdt(edtXStart, "5")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "5")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "5")
                }
            }

            R.id.btn_six_keyboard       ->
            {
                if (edtXStart.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXStart, "6")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "6")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "6")
                }

            }

            R.id.btn_seven_keyboard     ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    updateEdt(edtXStart, "7")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "7")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "7")
                }
            }

            R.id.btn_eight_keyboard     ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    updateEdt(edtXStart, "8")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "8")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "8")
                }
            }

            R.id.btn_nine_keyboard      ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    updateEdt(edtXStart, "9")
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    updateEdt(edtXEnd, "9")
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    updateEdt(edtXStep, "9")
                }
            }

            R.id.btn_dot_minus_keyboard ->
            {
                if (edtXStart.isFocused)
                {
                    if (edtXStart.selectionStart == 0 && isMinusInsertable(edtXStart))
                    {
                        updateEdt(edtXStart, "-")
                        return
                    }
                    if (isDotInsertable(edtXStart)) updateEdt(edtXStart, ".")
                }

                if (edtXEnd.isFocused)
                {
                    if (edtXEnd.selectionStart == 0 && isMinusInsertable(edtXEnd))
                    {
                        updateEdt(edtXEnd, "-")
                        return
                    }
                    if (isDotInsertable(edtXEnd)) updateEdt(edtXEnd, ".")
                }

                if (edtXStep.isFocused)
                {
                    if (isDotInsertable(edtXStep)) updateEdt(edtXStep, ".")
                }
            }

            R.id.btn_backspace_keyboard ->
            {
                if (edtXStart.isFocused)
                {
                    if (edtXStart.text.isNotEmpty())
                    {
                        backspaceFun(edtXStart)
                    }
                }

                if (edtXEnd.isFocused)
                {
                    if (edtXEnd.text.isNotEmpty())
                    {
                        backspaceFun(edtXEnd)
                    }
                }

                if (edtXStep.isFocused)
                {
                    if (edtXStep.text.isNotEmpty())
                    {
                        backspaceFun(edtXStep)
                    }
                }
            }

            R.id.btn_graph_build        ->
            {
                if (buildClickCount == 0)
                {
                    chart.data.clearValues()
                    chart.legend.isEnabled = false
                    chart.invalidate()
                }
                buildClickCount++
                if (edtExp.text.isEmpty() || edtXStep.text.isEmpty() || edtXStart.text.isEmpty() || edtXEnd.text.isEmpty())
                {
                    Snackbar.make(llParent, "Please fill all fields!", Snackbar.LENGTH_SHORT).show()
                    return
                }
                TransitionManager.beginDelayedTransition(llParent)
                linearLayoutButtons.visibility = View.GONE
                try
                {
                    buildGraph(edtExp.text.toString())
                }
                catch (e: Exception)
                {
                }

            }

            R.id.btn_x                  ->
            {
                if (isXInsertable())
                {
                    updateEdt(edtExp, "x")
                    currentNumDigits++
                }
                else return
            }


            R.id.btn_zero               ->
            {
                updateEdt(edtExp, "0")
                currentNumDigits++
            }

            R.id.btn_one                ->
            {

                updateEdt(edtExp, "1")
                currentNumDigits++
            }

            R.id.btn_two                ->
            {
                updateEdt(edtExp, "2")
                currentNumDigits++
            }

            R.id.btn_three              ->
            {
                updateEdt(edtExp, "3")
                currentNumDigits++
            }

            R.id.btn_four               ->
            {
                updateEdt(edtExp, "4")
                currentNumDigits++
            }

            R.id.btn_five               ->
            {
                updateEdt(edtExp, "5")
                currentNumDigits++
            }

            R.id.btn_six                ->
            {
                updateEdt(edtExp, "6")
                currentNumDigits++
            }

            R.id.btn_seven              ->
            {
                updateEdt(edtExp, "7")
                currentNumDigits++
            }

            R.id.btn_eight              ->
            {
                updateEdt(edtExp, "8")
                currentNumDigits++
            }

            R.id.btn_nine               ->
            {
                updateEdt(edtExp, "9")
                currentNumDigits++
            }

            R.id.btn_division           ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun(edtExp)
                    }
                }
                updateEdt(edtExp, "÷")
                currentNumDigits = 0
            }

            R.id.btn_multi              ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun(edtExp)
                    }
                }
                updateEdt(edtExp, "×")
                currentNumDigits = 0
            }

            R.id.btn_plus               ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun(edtExp)
                    }
                }
                updateEdt(edtExp, "+")
                currentNumDigits = 0
            }

            R.id.btn_minus              ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun(edtExp)
                    }
                }
                updateEdt(edtExp, "-")
                currentNumDigits = 0
            }

            R.id.btn_dot                ->
            {

                if (isDotInsertable(edtExp)) if (updateEdt(edtExp, ".")) currentNumDigits++
            }

            R.id.btn_backspace          ->
            {
                if (edtExp.text.isNotEmpty()) backspaceFun(edtExp)
                else return
            }

            R.id.btn_brackets           ->
            {
                addBrackets()
                currentNumDigits = 0
            }

            R.id.btn_square_root        ->
            {
                updateEdt(edtExp, "√(")
                currentNumDigits = 0
            }

            R.id.btn_exp                ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun(edtExp)
                    }
                }
                updateEdt(edtExp, "^")
                currentNumDigits = 0
            }

            R.id.btn_pi                 ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (edtExp.text[edtExp.text.toString().length - 1] == 'e' || edtExp.text[edtExp.text.toString().length - 1] == 'π') return
                }
                updateEdt(edtExp, "π")
                currentNumDigits = 0
            }

            R.id.btn_e                  ->
            {

                if (edtExp.text.isNotEmpty())
                {
                    if (edtExp.text[edtExp.text.toString().length - 1] == 'e' || edtExp.text[edtExp.text.toString().length - 1] == 'π') return
                }
                updateEdt(edtExp, "e")
                currentNumDigits = 0
            }

            R.id.btn_sin                ->
            {
                updateEdt(edtExp, "sin(")
                currentNumDigits = 0
            }

            R.id.btn_cos                ->
            {
                updateEdt(edtExp, "cos(")
                currentNumDigits = 0
            }

            R.id.btn_tan                ->
            {
                updateEdt(edtExp, "tan(")
                currentNumDigits = 0
            }


            R.id.btn_log2               ->
            {
                updateEdt(edtExp, "log2(")
                currentNumDigits = 0
            }

            R.id.btn_log10              ->
            {
                updateEdt(edtExp, "log10(")
                currentNumDigits = 0
            }

            R.id.btn_ln                 ->
            {
                updateEdt(edtExp, "ln(")
                currentNumDigits = 0
            }

        }
    }

    private fun isMinusInsertable(edt: EditText): Boolean
    {
        for (c in edt.text.toString())
        {
            if (c == '-') return false
        }
        return true
    }


    private fun isDotInsertable(edt: EditText): Boolean
    {
        if (edt.text.isEmpty()) return false

        val cursor = edt.selectionStart
        var i: Int = if (cursor == 0) cursor
        else cursor - 1
        var number = ""
        while (i >= 0)
        {
            if (!(checkForDigit(edt.text[i]) || edt.text[i] == '.')) break
            number = "${edt.text[i]}$number"
            i--
        }
        i = if (cursor == edt.text.lastIndex) cursor - 1
        else cursor
        while (i <= edt.text.lastIndex)
        {
            if (!(checkForDigit(edt.text[i]) || edt.text[i] == '.')) break
            number = "$number${edt.text[i]}"
            i++
        }
        if (number.isEmpty()) return false
        return !isStringHasDot(number)
    }


    private fun isStringHasDot(s: String): Boolean
    {
        for (element in s)
        {
            if (element == '.') return true
        }
        return false
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


    private fun isXInsertable(): Boolean
    {
        if (edtExp.text.isEmpty())
        {
            return true
        }
        when (edtExp.text.last())
        {
            'x' -> return false
            '0' -> return false
            '1' -> return false
            '2' -> return false
            '3' -> return false
            '4' -> return false
            '5' -> return false
            '6' -> return false
            '7' -> return false
            '8' -> return false
            '9' -> return false
            '.' -> return false
            'π' -> return false
            'e' -> return false
        }
        return true
    }


    private fun updateEdt(edt: EditText, strToAdd: String): Boolean
    {
        if (edt.text.length + strToAdd.length > 15)
        {
            return false
        }

        if (strToAdd.length == 1 && !checkForOperator(strToAdd[0]) && currentNumDigits >= 9)
        {
            return false
        }

        val prevString = edt.text.toString()

        val cursorPos = edt.selectionStart
        val leftString = prevString.substring(0, cursorPos)
        val rightString = prevString.substring(cursorPos)
        edt.setText(String.format("%s%s%s", leftString, strToAdd, rightString))
        edt.setSelection(cursorPos + strToAdd.length)

        return true

    }

    private fun checkForOperator(c: Char): Boolean
    {
        when (c)
        {
            '+' -> return true
            '-' -> return true
            '×' -> return true
            '÷' -> return true
            '^' -> return true
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


    private fun digitLimit(edt: EditText): Int
    {
        var currentNumDigits = 0
        if (edt.text.isEmpty()) return currentNumDigits
        val position = edt.selectionStart
        var i = if (position == 0) position
        else position - 1

        while (checkForNumber(edt.text[i]) || edt.text[i] == ',')
        {
            if (edt.text[i] == ',')
            {
                i--
                if (i < 0) break
                continue
            }
            currentNumDigits++
            i--
            if (i < 0) break
        }

        i = if (position == edt.text.length) position - 1
        else position

        while (checkForNumber(edt.text[i]) || edt.text[i] == ',')
        {
            if (edt.text[i] == ',')
            {
                i++
                if (i > edt.text.lastIndex) break
                continue
            }

            currentNumDigits++
            i++
            if (i > edt.text.lastIndex) break
        }

        return currentNumDigits
    }


    private fun mathFunDelete(edt: EditText): Boolean
    {
        val pos = edt.selectionStart
        val lastIndex = edt.text.lastIndex

        when (edt.text[pos - 1])
        {
            's' ->
            {
                if (edt.length() >= 4 && pos - 1 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 1, pos + 3) == "sin(")
                    {
                        edt.text.replace(pos - 1, pos + 3, "")

                        return true
                    }
                }
                if (edt.length() >= 4 && pos - 3 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 3, pos + 1) == "cos(")
                    {
                        edt.text.replace(pos - 3, pos + 1, "")

                        return true
                    }
                }

            }

            'i' ->
            {
                if (edt.length() >= 4 && pos - 2 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 2, pos + 2) == "sin(")
                    {
                        edt.text.replace(pos - 2, pos + 2, "")

                        return true
                    }
                }
            }

            'n' ->
            {
                if (edt.length() >= 4 && pos - 3 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 3, pos + 1) == "sin(")
                    {
                        edt.text.replace(pos - 3, pos + 1, "")

                        return true
                    }
                    if (edt.text.substring(pos - 3, pos + 1) == "tan(")
                    {
                        edt.text.replace(pos - 3, pos + 1, "")

                        return true
                    }
                }
                if (pos - 2 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 2, pos + 1) == "ln(")
                    {
                        edt.text.replace(pos - 2, pos + 1, "")

                        return true
                    }
                }
            }

            'c' ->
            {
                if (edt.length() >= 4 && pos - 1 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 1, pos + 3) == "cos(")
                    {
                        edt.text.replace(pos - 1, pos + 3, "")

                        return true
                    }
                }
            }

            'o' ->
            {
                if (edt.length() >= 4 && pos - 2 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 2, pos + 2) == "cos(")
                    {
                        edt.text.replace(pos - 2, pos + 2, "")

                        return true
                    }
                }
                if (edt.length() >= 5 && pos - 2 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 2, pos + 3) == "log2(")
                    {
                        edt.text.replace(pos - 2, pos + 3, "")

                        return true
                    }
                }
                if (edt.length() >= 6 && pos - 2 >= 0 && pos + 4 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 2, pos + 4) == "log10(")
                    {
                        edt.text.replace(pos - 2, pos + 4, "")

                        return true
                    }
                }
            }

            't' ->
            {
                if (edt.length() >= 4 && pos - 1 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 1, pos + 3) == "tan(")
                    {
                        edt.text.replace(pos - 1, pos + 3, "")

                        return true
                    }
                }
            }

            'a' ->
            {
                if (edt.length() >= 4 && pos - 2 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 2, pos + 2) == "tan(" || edt.text.substring(
                            pos - 2, pos + 2) == "rad("
                    )
                    {
                        edt.text.replace(pos - 2, pos + 2, "")

                        return true
                    }
                }
            }

            'l' ->
            {
                if (pos - 1 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 1, pos + 2) == "ln(")
                    {
                        edt.text.replace(pos - 1, pos + 2, "")

                        return true
                    }
                }
                if (edt.length() >= 5 && pos - 1 >= 0 && pos + 4 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 1, pos + 4) == "log2(")
                    {
                        edt.text.replace(pos - 1, pos + 4, "")

                        return true
                    }
                }
                if (edt.length() >= 6 && pos - 1 >= 0 && pos + 5 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 1, pos + 5) == "log10(")
                    {
                        edt.text.replace(pos - 1, pos + 5, "")

                        return true
                    }
                }
            }

            'g' ->
            {
                if (edt.length() >= 5 && pos - 3 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 3, pos + 2) == "log2(")
                    {
                        edt.text.replace(pos - 3, pos + 2, "")

                        return true
                    }
                }
                if (edt.length() >= 6 && pos - 3 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 3, pos + 3) == "log10(")
                    {
                        edt.text.replace(pos - 3, pos + 3, "")

                        return true
                    }
                }
            }

            '1' ->
            {
                if (edt.length() >= 6 && pos - 4 >= 0 && pos + 2 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 4, pos + 2) == "log10(")
                    {
                        edt.text.replace(pos - 4, pos + 2, "")

                        return true
                    }
                }
            }

            '0' ->
            {
                if (edt.length() >= 6 && pos - 5 >= 0 && pos + 1 <= lastIndex + 1)
                {

                    if (edt.text.substring(pos - 5, pos + 1) == "log10(")
                    {
                        edt.text.replace(pos - 5, pos + 1, "")

                        return true
                    }
                }
            }

            '2' ->
            {
                if (edt.length() >= 5 && pos - 4 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 4, pos + 1) == "log2(")
                    {
                        edt.text.replace(pos - 4, pos + 1, "")

                        return true
                    }
                }
            }

            'r' ->
            {
                if (edt.length() >= 4 && pos - 1 >= 0 && pos + 3 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 1, pos + 3) == "rad(")
                    {
                        edt.text.replace(pos - 1, pos + 3, "")

                        return true
                    }
                }
            }

            'd' ->
            {
                if (edt.length() >= 4 && pos - 3 >= 0 && pos + 1 <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 3, pos + 1) == "rad(")
                    {
                        edt.text.replace(pos - 3, pos + 1, "")

                        return true
                    }
                }
            }

            '(' ->
            {

                if (pos - 3 >= 0 && pos <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 3, pos) == "ln(")
                    {
                        edt.text.replace(pos - 3, pos, "")

                        return true
                    }
                }
                if (edt.length() >= 4 && pos - 4 >= 0 && pos <= lastIndex + 1)
                {
                    if (edt.text.substring(pos - 4, pos) == "sin(" || edt.text.substring(pos - 4,
                            pos) == "cos(" || edt.text.substring(pos - 4,
                            pos) == "tan(" || edt.text.substring(pos - 4, pos) == "rad("
                    )
                    {
                        edt.text.replace(pos - 4, pos, "")

                        return true
                    }
                }
                if (edt.length() >= 5 && pos - 5 >= 0 && pos <= lastIndex + 1 + 1)
                {
                    if (edt.text.substring(pos - 5, pos) == "log2(")
                    {
                        edt.text.replace(pos - 5, pos, "")

                        return true
                    }
                }
                if (edt.length() >= 6 && pos - 6 >= 0 && pos <= lastIndex + 1) if (edt.text.substring(
                        pos - 6, pos) == "log10("
                )
                {
                    edt.text.replace(pos - 6, pos, "")

                    return true
                }
            }


        }
        return false
    }

    private fun backspaceFun(edt: EditText)
    {
        if (edt.length() == 0 || edt.selectionStart == 0 || digitLimit(edt) >= MAX_DIGITS)
        {
            return
        }

        if (edt.length() >= 3) if (mathFunDelete(edt))
        {
            return
        }


        val cursorPos = edt.selectionStart
        val textLength = edt.text.length

        if (cursorPos != 0 && textLength != 0)
        {
            deleteLast(edt)
        }
        return
    }


    private fun deleteLast(edt: EditText)
    {
        val cursorPos = edt.selectionStart
        val selection: SpannableStringBuilder = edt.text as SpannableStringBuilder
        selection.replace(cursorPos - 1, cursorPos, "")
        edt.text = selection
        edt.setSelection(cursorPos - 1)
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun initPopUpMenu()
    {
        val popupMenu = PopupMenu(this, btnMenu)
        popupMenu.inflate(R.menu.menu_graph)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId)
            {
                R.id.menu_clear_graph ->
                {
                    if (chart.data.dataSetCount != 0)
                    {
                        chart.data.clearValues()
                        chart.legend.isEnabled = false
                        chart.invalidate()
                    }
                }

            }
            true
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

        btnMenu.setOnClickListener {
            popupMenu.show()
        }
    }


    private fun checkForDot(s: String): Boolean
    {
        for (element in s)
        {
            if (element == '.') return true
        }
        return false
    }

    private fun addBrackets()
    {
        val cursorPos = edtExp.selectionStart
        val textLength = edtExp.text.length
        var openBracketCount = 0
        var closeBracketCount = 0

        if (textLength == 0)
        {
            updateEdt(edtExp, "(")
            edtExp.setSelection(cursorPos + 1)
            return
        }

        if (edtExp.text.toString()[cursorPos - 1] == '+' || edtExp.text.toString()[cursorPos - 1] == '-' || edtExp.text.toString()[cursorPos - 1] == '×' || edtExp.text.toString()[cursorPos - 1] == '÷' || edtExp.text.toString()[cursorPos - 1] == '^' || edtExp.text.toString()[cursorPos - 1] == '!' || edtExp.text.toString()[cursorPos - 1] == '√')
        {
            updateEdt(edtExp, "(")
            edtExp.setSelection(cursorPos + 1)
            return
        }

        for (i in 0 until cursorPos)
        {
            if (edtExp.text.toString().substring(i, i + 1) == "(")
            {
                openBracketCount++
            }
            if (edtExp.text.toString().substring(i, i + 1) == ")")
            {
                closeBracketCount++
            }
        }

        if (openBracketCount == closeBracketCount || edtExp.text.toString()
                .substring(textLength - 1, textLength) == "("
        )
        {
            if (updateEdt(edtExp, "(")) edtExp.setSelection(cursorPos + 1)
        }
        else if (closeBracketCount < openBracketCount && edtExp.text.toString()
                .substring(textLength - 1, textLength) != "("
        )
        {
            if (updateEdt(edtExp, ")")) edtExp.setSelection(cursorPos + 1)
        }

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
            btnZeroKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnOneKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnTwoKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnThreeKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnFourKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnFiveKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnSixKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnSevenKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnEightKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnNineKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnDotKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
            btnBackspaceKeyboard.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(numbersColor))
        }
        else setDefaultNumberColor()

        if (actionsColor.isNotEmpty())
        {
            btnPlus.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnMinus.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnMulti.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnDivision.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnExp.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnPi.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnSquareRoot.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnBrackets.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnSin.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnCos.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnTan.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnE.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnLg.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnLn.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
            btnLog2.backgroundTintList = ColorStateList.valueOf(Color.parseColor(actionsColor))
        }
        else setDefaultActionsColor()


        if (acColor.isNotEmpty())
        {
            btnX.backgroundTintList = ColorStateList.valueOf(Color.parseColor(acColor))
        }

        if (equalColor.isNotEmpty())
        {
            btnBuild.backgroundTintList = ColorStateList.valueOf(Color.parseColor(equalColor))
        }


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

            btnZero.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnOne.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnTwo.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnThree.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnFour.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnFive.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnSix.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnSeven.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnEight.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnNine.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnBackSpace.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnDot.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))

            btnZeroKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnOneKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnTwoKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnThreeKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnFourKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnFiveKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnSixKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnSevenKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnEightKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnNineKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnDotKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.white))
            btnBackspaceKeyboard.setTextColor(
                AppCompatResources.getColorStateList(this, R.color.white))
        }
        else
        {
            btnZero.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnOne.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnTwo.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnThree.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnFour.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnFive.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnSix.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnSeven.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnEight.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnNine.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnBackSpace.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnDot.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))

            btnZeroKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnOneKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnTwoKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnThreeKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnFourKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnFiveKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnSixKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnSevenKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnEightKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnNineKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnDotKeyboard.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
            btnBackspaceKeyboard.setTextColor(
                AppCompatResources.getColorStateList(this, R.color.black))
        }


        btnX.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))

        btnPlus.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnMinus.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnMulti.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnDivision.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnExp.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnPi.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnSquareRoot.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnBrackets.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnSin.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnCos.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnTan.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnE.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnLg.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnLn.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))
        btnLog2.setTextColor(AppCompatResources.getColorStateList(this, R.color.black))

    }

    private fun setDefaultActionsColor()
    {
        btnPlus.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnMinus.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnMulti.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnDivision.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnExp.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnPi.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnSquareRoot.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnBrackets.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnSin.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnCos.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnTan.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnE.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnLg.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnLn.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
        btnLog2.backgroundTintList = AppCompatResources.getColorStateList(this, R.color.light_blue)
    }

    private fun setDefaultNumberColor()
    {
        if (isDarkThemeOn())
        {
            btnZero.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnOne.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnTwo.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnThree.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnFour.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnFive.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnSix.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnSeven.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnEight.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnNine.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnDot.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnBackSpace.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnZeroKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnOneKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnTwoKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnThreeKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnFourKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnFiveKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnSixKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnSevenKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnEightKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnNineKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnDotKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
            btnBackspaceKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.dark_gray)
        }
        else
        {
            btnZero.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnOne.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnTwo.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnThree.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnFour.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnFive.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnSix.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnSeven.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnEight.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnNine.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnDot.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnBackSpace.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnZeroKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnOneKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnTwoKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnThreeKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnFourKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnFiveKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnSixKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnSevenKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnEightKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnNineKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnDotKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
            btnBackspaceKeyboard.backgroundTintList =
                AppCompatResources.getColorStateList(this, R.color.light_gray)
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
        btnBackSpace.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnDot.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))

        btnX.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))

        btnPlus.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnMinus.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnMulti.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnDivision.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnExp.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnPi.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnSquareRoot.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnBrackets.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnSin.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnCos.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnTan.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnE.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnLg.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnLn.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnLog2.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))

        btnZeroKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnOneKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnTwoKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnThreeKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnFourKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnFiveKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnSixKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnSevenKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnEightKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnNineKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnDotKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))
        btnBackspaceKeyboard.setTextColor(ColorStateList.valueOf(Color.parseColor(textColor)))


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
        btnExp.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnPi.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnSquareRoot.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnBrackets.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnSin.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnCos.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnTan.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnE.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnLg.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnLn.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnLog2.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnX.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnBuild.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)

        btnZeroKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnOneKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnTwoKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnThreeKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnFourKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnFiveKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnSixKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnSevenKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnEightKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnNineKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnDotKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)
        btnBackspaceKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_circle)

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
        btnExp.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnPi.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnSquareRoot.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnBrackets.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnSin.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnCos.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnTan.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnE.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnLg.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnLn.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnLog2.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnX.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnBuild.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)


        btnZeroKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnOneKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnTwoKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnThreeKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnFourKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnFiveKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnSixKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnSevenKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnEightKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnNineKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnDotKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
        btnBackspaceKeyboard.background =
            ContextCompat.getDrawable(this, R.drawable.buttons_rectangle)
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
        btnExp.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnPi.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnSquareRoot.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnBrackets.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnSin.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnCos.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnTan.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnE.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnLg.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnLn.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnLog2.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnX.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnBuild.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)


        btnZeroKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnOneKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnTwoKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnThreeKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnFourKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnFiveKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnSixKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnSevenKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnEightKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnNineKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnDotKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)
        btnBackspaceKeyboard.background = ContextCompat.getDrawable(this, R.drawable.buttons_round)

    }
}
