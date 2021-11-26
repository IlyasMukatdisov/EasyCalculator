package com.gmail.mukatdisovilyas.easycalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.transition.TransitionManager
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

    private var isExpanded = true

    private lateinit var drkGray: ColorStateList
    private lateinit var whiteColor: ColorStateList


    private lateinit var llMenu: LinearLayout

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
    private lateinit var btnMinusKeyboard: Button

    private lateinit var btnX: Button
    private lateinit var btnKeyboard: Button
    private lateinit var btnBuild: ImageButton
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
    private lateinit var btnClearEdt: ImageButton

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
        else if (isDarkThemeOn()) setDefaultNightTheme()

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

    private fun setDefaultNightTheme()
    {
        btnZero.backgroundTintList = drkGray
        btnOne.backgroundTintList = drkGray
        btnTwo.backgroundTintList = drkGray
        btnThree.backgroundTintList = drkGray
        btnFour.backgroundTintList = drkGray
        btnFive.backgroundTintList = drkGray
        btnSix.backgroundTintList = drkGray
        btnSeven.backgroundTintList = drkGray
        btnEight.backgroundTintList = drkGray
        btnNine.backgroundTintList = drkGray
        btnDot.backgroundTintList = drkGray
        btnBackSpace.backgroundTintList = drkGray

        btnMinusKeyboard.backgroundTintList = drkGray

        edtExp.backgroundTintList = drkGray

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

        btnMinusKeyboard.setTextColor(whiteColor)


        btnZeroKeyboard.backgroundTintList = drkGray
        btnOneKeyboard.backgroundTintList = drkGray
        btnTwoKeyboard.backgroundTintList = drkGray
        btnThreeKeyboard.backgroundTintList = drkGray
        btnFourKeyboard.backgroundTintList = drkGray
        btnFiveKeyboard.backgroundTintList = drkGray
        btnSixKeyboard.backgroundTintList = drkGray
        btnSevenKeyboard.backgroundTintList = drkGray
        btnEightKeyboard.backgroundTintList = drkGray
        btnNineKeyboard.backgroundTintList = drkGray
        btnDotKeyboard.backgroundTintList = drkGray
        btnBackspaceKeyboard.backgroundTintList = drkGray


        btnZeroKeyboard.setTextColor(whiteColor)
        btnOneKeyboard.setTextColor(whiteColor)
        btnTwoKeyboard.setTextColor(whiteColor)
        btnThreeKeyboard.setTextColor(whiteColor)
        btnFourKeyboard.setTextColor(whiteColor)
        btnFiveKeyboard.setTextColor(whiteColor)
        btnSixKeyboard.setTextColor(whiteColor)
        btnSevenKeyboard.setTextColor(whiteColor)
        btnEightKeyboard.setTextColor(whiteColor)
        btnNineKeyboard.setTextColor(whiteColor)
        btnDotKeyboard.setTextColor(whiteColor)
        btnBackspaceKeyboard.setTextColor(whiteColor)
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
                        if (isDarkThemeOn())
                            btnMenu.backgroundTintList =
                                drkGray
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(Color.GRAY)
                    }
                    MotionEvent.ACTION_UP ->
                    {
                        if (isDarkThemeOn())
                            btnMenu.backgroundTintList =
                                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
                        else btnMenu.backgroundTintList =
                            whiteColor
                        view.performClick()
                    }
                }
            }

            R.id.ll_menu ->
            {
                when (motionEvent.action)
                {
                    MotionEvent.ACTION_DOWN ->
                    {
                        if (isDarkThemeOn())
                            btnMenu.backgroundTintList =
                                drkGray
                        else btnMenu.backgroundTintList =
                            ColorStateList.valueOf(Color.GRAY)
                    }
                    MotionEvent.ACTION_UP ->
                    {
                        if (isDarkThemeOn())
                            btnMenu.backgroundTintList =
                                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
                        else btnMenu.backgroundTintList =
                            whiteColor
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
        btnMinusKeyboard.textSize = viewsTextSize * 3 / 2

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
        drkGray = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray))
        whiteColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))

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

        btnKeyboard = findViewById(R.id.btn_keyboard)

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
        btnClearEdt = findViewById(R.id.btn_clear_edt)

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
        btnDotKeyboard = findViewById(R.id.btn_dot_keyboard)
        btnBackspaceKeyboard = findViewById(R.id.btn_backspace_keyboard)
        btnMinusKeyboard = findViewById(R.id.btn_minus_keyboard)


        tvFx = findViewById(R.id.tv_fx)
        tvXValues = findViewById(R.id.tv_x_values)
        tvSemicolon = findViewById(R.id.tv_semicolon)
        tvXStep = findViewById(R.id.tv_x_step)

        edtExp.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                btnKeyboard.text = getString(R.string.btn_more_expanded)
                isExpanded = true
                TransitionManager.beginDelayedTransition(llParent)
                linearLayoutButtons.visibility = View.VISIBLE

                llKeyboard.visibility = View.GONE
            }
            else
            {
                btnKeyboard.text = getString(R.string.btn_more_not_expanded)
                isExpanded = false
                TransitionManager.beginDelayedTransition(llParent)

                linearLayoutButtons.visibility = View.GONE
                llKeyboard.visibility = View.GONE
            }
        }

        edtXStart.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                btnKeyboard.text = getString(R.string.btn_more_expanded)
                isExpanded = true
                TransitionManager.beginDelayedTransition(llParent)

                llKeyboard.visibility = View.VISIBLE
                linearLayoutButtons.visibility = View.GONE
            }
            else
            {
                btnKeyboard.text = getString(R.string.btn_more_not_expanded)
                isExpanded = false
                TransitionManager.beginDelayedTransition(llParent)

                llKeyboard.visibility = View.GONE
                linearLayoutButtons.visibility = View.GONE
            }
        }
        edtXEnd.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                btnKeyboard.text = getString(R.string.btn_more_expanded)
                isExpanded = true
                TransitionManager.beginDelayedTransition(llParent)

                llKeyboard.visibility = View.VISIBLE
                linearLayoutButtons.visibility = View.GONE
            }
            else
            {
                btnKeyboard.text = getString(R.string.btn_more_not_expanded)
                isExpanded = false
                TransitionManager.beginDelayedTransition(llParent)

                llKeyboard.visibility = View.GONE
                linearLayoutButtons.visibility = View.GONE
            }
        }
        edtXStep.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {

                btnKeyboard.text = getString(R.string.btn_more_expanded)
                isExpanded = true
                TransitionManager.beginDelayedTransition(llParent)

                llKeyboard.visibility = View.VISIBLE
                linearLayoutButtons.visibility = View.GONE
            }
            else
            {
                btnKeyboard.text = getString(R.string.btn_more_not_expanded)
                isExpanded = false
                TransitionManager.beginDelayedTransition(llParent)

                llKeyboard.visibility = View.GONE
                linearLayoutButtons.visibility = View.GONE
            }
        }
        llMenu.setOnTouchListener(this)
        btnMenu.setOnTouchListener(this)
        llParent = findViewById(R.id.ll_parent)
        TransitionManager.beginDelayedTransition(llParent)


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
        btnClearEdt.setOnClickListener(this)
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
        btnMinusKeyboard.setOnClickListener(this)

        edtExp.setOnClickListener(this)
        edtXStart.setOnClickListener(this)
        edtXEnd.setOnClickListener(this)
        edtXStep.setOnClickListener(this)

        btnKeyboard.setOnClickListener(this)
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
            Color.argb(
                255, 100 + rnd.nextInt(156), 100 + rnd.nextInt(156),
                100 + rnd.nextInt(156)
            )
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
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onClick(v: View?)
    {
        var temp: String
        when (v?.id)
        {

            R.id.edt_graph ->
            {
                btnKeyboard.text = getString(R.string.btn_more_expanded)
                isExpanded = true
                linearLayoutButtons.visibility = View.VISIBLE
                llKeyboard.visibility = View.GONE
            }

            R.id.edt_x_begin ->
            {
                btnKeyboard.text = getString(R.string.btn_more_expanded)
                isExpanded = true
                linearLayoutButtons.visibility = View.GONE
                llKeyboard.visibility = View.VISIBLE
            }

            R.id.edt_x_end ->
            {
                btnKeyboard.text = getString(R.string.btn_more_expanded)
                isExpanded = true
                linearLayoutButtons.visibility = View.GONE
                llKeyboard.visibility = View.VISIBLE
            }

            R.id.edt_x_step ->
            {
                btnKeyboard.text = getString(R.string.btn_more_expanded)
                isExpanded = true
                linearLayoutButtons.visibility = View.GONE
                llKeyboard.visibility = View.VISIBLE
            }


            R.id.btn_minus_keyboard ->
            {
                if (edtXStart.isFocused)
                {
                    if (edtXStart.text.isEmpty() && edtXStart.text.length < 5)
                    {
                        val position = edtXStart.selectionStart
                        edtXStart.setText("-")
                        edtXStart.setSelection(position + 1)
                    }
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {

                    if (edtXEnd.text.isEmpty())
                    {
                        val position = edtXEnd.selectionStart
                        edtXEnd.setText("-")
                        edtXEnd.setSelection(position + 1)
                    }
                }

                if (edtXStep.isFocused)
                {
                    return
                }
            }


            R.id.btn_keyboard ->
            {
                if (isExpanded)
                {
                    TransitionManager.beginDelayedTransition(llParent)

                    linearLayoutButtons.visibility = View.GONE
                    llKeyboard.visibility = View.GONE
                    btnKeyboard.text = getString(R.string.btn_more_not_expanded)
                    isExpanded = false
                }
                else
                {

                    if (edtExp.isFocused || (!edtXStart.isFocused && !edtXEnd.isFocused && !edtXStep.isFocused && !edtExp.isFocused))
                    {
                        TransitionManager.beginDelayedTransition(llParent)

                        linearLayoutButtons.visibility = View.VISIBLE
                        llKeyboard.visibility = View.GONE
                    }
                    if (edtXStart.isFocused || edtXEnd.isFocused || edtXStep.isFocused)
                    {
                        TransitionManager.beginDelayedTransition(llParent)

                        llKeyboard.visibility = View.VISIBLE
                        linearLayoutButtons.visibility = View.GONE
                    }
                    btnKeyboard.text = getString(R.string.btn_more_expanded)
                    isExpanded = true
                }
            }

            R.id.btn_zero_keyboard ->
            {

                if (edtXStart.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXStart.text}0"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}0"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}0"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }
            }

            R.id.btn_one_keyboard ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    temp = "${edtXStart.text}1"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}1"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}1"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }
            }

            R.id.btn_two_keyboard ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    temp = "${edtXStart.text}2"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}2"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}2"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }
            }

            R.id.btn_three_keyboard ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    temp = "${edtXStart.text}3"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}3"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}3"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }
            }

            R.id.btn_four_keyboard ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    temp = "${edtXStart.text}4"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}4"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}4"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }
            }

            R.id.btn_five_keyboard ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    temp = "${edtXStart.text}5"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}5"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}5"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }
            }

            R.id.btn_six_keyboard ->
            {
                if (edtXStart.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXStart.text}6"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}6"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}6"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }

            }

            R.id.btn_seven_keyboard ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    temp = "${edtXStart.text}7"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}7"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}7"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }
            }

            R.id.btn_eight_keyboard ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    temp = "${edtXStart.text}8"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}8"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}8"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }
            }

            R.id.btn_nine_keyboard ->
            {
                if (edtXStart.isFocused && edtXStart.text.length < 5)
                {
                    temp = "${edtXStart.text}9"
                    val position = edtXStart.selectionStart
                    edtXStart.setText(temp)
                    edtXStart.setSelection(position + 1)
                }

                if (edtXEnd.isFocused && edtXEnd.text.length < 5)
                {
                    temp = "${edtXEnd.text}9"
                    val position = edtXEnd.selectionStart
                    edtXEnd.setText(temp)
                    edtXEnd.setSelection(position + 1)
                }

                if (edtXStep.isFocused && edtXStep.text.length < 5)
                {
                    temp = "${edtXStep.text}9"
                    val position = edtXStep.selectionStart
                    edtXStep.setText(temp)
                    edtXStep.setSelection(position + 1)
                }
            }

            R.id.btn_dot_keyboard ->
            {
                if (edtXStart.isFocused)
                {
                    if (!checkForDot(edtXStart.text.toString()) && edtXStart.text.length < 5)
                    {
                        temp = "${edtXStart.text}."
                        val position = edtXStart.selectionStart
                        edtXStart.setText(temp)
                        edtXStart.setSelection(position + 1)
                    }
                }

                if (edtXEnd.isFocused)
                {
                    if (!checkForDot(edtXEnd.text.toString()) && edtXEnd.text.length < 5)
                    {
                        temp = "${edtXEnd.text}."
                        val position = edtXEnd.selectionStart
                        edtXEnd.setText(temp)
                        edtXEnd.setSelection(position + 1)
                    }
                }

                if (edtXStep.isFocused)
                {
                    if (!checkForDot(edtXStep.text.toString()) && edtXStep.text.length < 5)
                    {
                        temp = "${edtXStep.text}."
                        val position = edtXStep.selectionStart
                        edtXStep.setText(temp)
                        edtXStep.setSelection(position + 1)
                    }
                }
            }

            R.id.btn_backspace_keyboard ->
            {
                if (edtXStart.isFocused)
                {
                    if (edtXStart.text.isNotEmpty())
                    {
                        val cursorPos = edtXStart.selectionStart
                        val textLength = edtXStart.text.length

                        val lastChar = edtXStart.text.toString()[textLength - 1]

                        if (cursorPos != 0 && textLength != 0)
                        {
                            val selection: SpannableStringBuilder =
                                edtXStart.text as SpannableStringBuilder
                            selection.replace(cursorPos - 1, cursorPos, "")
                            if (!checkForOperator(lastChar))
                            {
                                currentNumDigits--
                            }
                            edtXStart.text = selection
                            edtXStart.setSelection(cursorPos - 1)
                        }
                    }
                }

                if (edtXEnd.isFocused)
                {
                    if (edtXEnd.text.isNotEmpty())
                    {
                        val cursorPos = edtXEnd.selectionStart
                        val textLength = edtXEnd.text.length

                        val lastChar = edtXEnd.text.toString()[textLength - 1]

                        if (cursorPos != 0 && textLength != 0)
                        {
                            val selection: SpannableStringBuilder =
                                edtXEnd.text as SpannableStringBuilder
                            selection.replace(cursorPos - 1, cursorPos, "")
                            if (!checkForOperator(lastChar))
                            {
                                currentNumDigits--
                            }
                            edtXEnd.text = selection
                            edtXEnd.setSelection(cursorPos - 1)
                        }
                    }
                }

                if (edtXStep.isFocused)
                {
                    if (edtXStep.text.isNotEmpty())
                    {
                        val cursorPos = edtXStep.selectionStart
                        val textLength = edtXStep.text.length

                        val lastChar = edtXStep.text.toString()[textLength - 1]

                        if (cursorPos != 0 && textLength != 0)
                        {
                            val selection: SpannableStringBuilder =
                                edtXStep.text as SpannableStringBuilder
                            selection.replace(cursorPos - 1, cursorPos, "")
                            if (!checkForOperator(lastChar))
                            {
                                currentNumDigits--
                            }
                            edtXStep.text = selection
                            edtXStep.setSelection(cursorPos - 1)
                        }
                    }
                }
            }


            R.id.btn_clear_edt ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    edtExp.setText("")
                }
            }

            R.id.btn_graph_build ->
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

            R.id.btn_x ->
            {
                if (isXInsertable())
                {
                    updateEdtExp("x")
                    currentNumDigits++
                }
                else return
            }


            R.id.btn_zero ->
            {
                updateEdtExp("0")
                currentNumDigits++
            }

            R.id.btn_one ->
            {

                updateEdtExp("1")
                currentNumDigits++
            }

            R.id.btn_two ->
            {
                updateEdtExp("2")
                currentNumDigits++
            }

            R.id.btn_three ->
            {
                updateEdtExp("3")
                currentNumDigits++
            }

            R.id.btn_four ->
            {
                updateEdtExp("4")
                currentNumDigits++
            }

            R.id.btn_five ->
            {
                updateEdtExp("5")
                currentNumDigits++
            }

            R.id.btn_six ->
            {
                updateEdtExp("6")
                currentNumDigits++
            }

            R.id.btn_seven ->
            {
                updateEdtExp("7")
                currentNumDigits++
            }

            R.id.btn_eight ->
            {
                updateEdtExp("8")
                currentNumDigits++
            }

            R.id.btn_nine ->
            {
                updateEdtExp("9")
                currentNumDigits++
            }

            R.id.btn_division ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun()
                    }
                }
                updateEdtExp("÷")
                currentNumDigits = 0
            }

            R.id.btn_multi ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun()
                    }
                }
                updateEdtExp("×")
                currentNumDigits = 0
            }

            R.id.btn_plus ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun()
                    }
                }
                updateEdtExp("+")
                currentNumDigits = 0
            }

            R.id.btn_minus ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun()
                    }
                }
                updateEdtExp("-")
                currentNumDigits = 0
            }

            R.id.btn_dot ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (!checkForDot(edtExp.text.toString())) updateEdtExp(".")
                    else return
                }
                else return
            }

            R.id.btn_backspace ->
            {
                if (edtExp.text.isNotEmpty()) backspaceFun()
                else return
            }

            R.id.btn_brackets ->
            {
                addBrackets()
                currentNumDigits = 0
            }

            R.id.btn_square_root ->
            {
                updateEdtExp("√(")
                currentNumDigits = 0
            }

            R.id.btn_exp ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (checkForOperator(edtExp.text[edtExp.text.lastIndex]))
                    {
                        backspaceFun()
                    }
                }
                updateEdtExp("^")
                currentNumDigits = 0
            }

            R.id.btn_pi ->
            {
                if (edtExp.text.isNotEmpty())
                {
                    if (edtExp.text[edtExp.text.toString().length - 1] == 'e'
                        || edtExp.text[edtExp.text.toString().length - 1] == 'π'
                    ) return
                }
                updateEdtExp("π")
                currentNumDigits = 0
            }

            R.id.btn_e ->
            {

                if (edtExp.text.isNotEmpty())
                {
                    if (edtExp.text[edtExp.text.toString().length - 1] == 'e'
                        || edtExp.text[edtExp.text.toString().length - 1] == 'π'
                    ) return
                }
                updateEdtExp("e")
                currentNumDigits = 0
            }

            R.id.btn_sin ->
            {
                updateEdtExp("sin(")
                currentNumDigits = 0
            }

            R.id.btn_cos ->
            {
                updateEdtExp("cos(")
                currentNumDigits = 0
            }

            R.id.btn_tan ->
            {
                updateEdtExp("tan(")
                currentNumDigits = 0
            }


            R.id.btn_log2 ->
            {
                updateEdtExp("log2(")
                currentNumDigits = 0
            }

            R.id.btn_log10 ->
            {
                updateEdtExp("log10(")
                currentNumDigits = 0
            }

            R.id.btn_ln ->
            {
                updateEdtExp("ln(")
                currentNumDigits = 0
            }

        }
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


    private fun updateEdtExp(strToAdd: String): Boolean
    {
        if (edtExp.text.length + strToAdd.length > 15)
        {
            return false
        }

        if (strToAdd.length == 1 && !checkForOperator(
                strToAdd[0]
            ) && currentNumDigits >= 9
        )
        {
            return false
        }

        val prevString = edtExp.text.toString()

        val cursorPos = edtExp.selectionStart
        val leftString = prevString.substring(0, cursorPos)
        val rightString = prevString.substring(cursorPos)
        edtExp.setText(String.format("%s%s%s", leftString, strToAdd, rightString))
        edtExp.setSelection(cursorPos + strToAdd.length)

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

    private fun backspaceFun()
    {
        if (edtExp.text.isEmpty())
        {
            return
        }
        var cursorPos = edtExp.selectionStart
        val textLength = edtExp.text.length

        val lastChar = edtExp.text.toString()[textLength - 1]


        if (edtExp.length() >= 3)
        {
            val a = edtExp.text.toString().substring(edtExp.text.lastIndex - 2)
            if (a == "ln(")
            {
                edtExp.text.replace(edtExp.text.lastIndex - 2, edtExp.text.length, "")
                edtExp.setSelection(cursorPos - 3)
                cursorPos -= 3
            }

            if (edtExp.length() >= 4)
            {
                val b = edtExp.text.toString().substring(edtExp.text.lastIndex - 3)
                if (b == "sin(" || b == "cos(" || b == "tan(")
                {
                    edtExp.text.replace(edtExp.text.lastIndex - 3, edtExp.text.length, "")
                    edtExp.setSelection(cursorPos - 4)
                    cursorPos -= 4
                }
            }

            if (textLength >= 5)
            {
                val b = edtExp.text.toString().substring(edtExp.text.lastIndex - 4)
                if ("log2(" in b)
                {
                    edtExp.text.replace(edtExp.text.lastIndex - 4, edtExp.text.length, "")
                    edtExp.setSelection(cursorPos - 5)
                    cursorPos -= 5
                }
            }

            if (textLength >= 6)
            {
                val c = edtExp.text.toString().substring(edtExp.text.lastIndex - 5)
                if ("log10(" in c)
                {
                    edtExp.text.replace(edtExp.text.lastIndex - 5, edtExp.text.length, "")
                    edtExp.setSelection(cursorPos - 6)
                    cursorPos -= 6
                }
            }


        }


        if (cursorPos != 0 && textLength != 0)
        {
            val selection: SpannableStringBuilder =
                edtExp.text as SpannableStringBuilder
            selection.replace(cursorPos - 1, cursorPos, "")
            if (!checkForOperator(lastChar))
            {
                currentNumDigits--
            }
            edtExp.text = selection
            edtExp.setSelection(cursorPos - 1)
        }

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
            updateEdtExp("(")
            edtExp.setSelection(cursorPos + 1)
            return
        }

        if (edtExp.text.toString()[cursorPos - 1] == '+' || edtExp.text.toString()[cursorPos - 1] == '-' ||
            edtExp.text.toString()[cursorPos - 1] == '×' || edtExp.text.toString()[cursorPos - 1] == '÷' ||
            edtExp.text.toString()[cursorPos - 1] == '^' || edtExp.text.toString()[cursorPos - 1] == '!' ||
            edtExp.text.toString()[cursorPos - 1] == '√'
        )
        {
            updateEdtExp("(")
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
            if (updateEdtExp("(")) edtExp.setSelection(cursorPos + 1)
        }
        else if (closeBracketCount < openBracketCount && edtExp.text.toString()
                .substring(textLength - 1, textLength) != "("
        )
        {
            if (updateEdtExp(")")) edtExp.setSelection(cursorPos + 1)
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
            btnZeroKeyboard.backgroundTintList = nColor
            btnOneKeyboard.backgroundTintList = nColor
            btnTwoKeyboard.backgroundTintList = nColor
            btnThreeKeyboard.backgroundTintList = nColor
            btnFourKeyboard.backgroundTintList = nColor
            btnFiveKeyboard.backgroundTintList = nColor
            btnSixKeyboard.backgroundTintList = nColor
            btnSevenKeyboard.backgroundTintList = nColor
            btnEightKeyboard.backgroundTintList = nColor
            btnNineKeyboard.backgroundTintList = nColor
            btnDotKeyboard.backgroundTintList = nColor
            btnBackspaceKeyboard.backgroundTintList = nColor
            btnMinusKeyboard.backgroundTintList = nColor
        }

        if (actionsColor.isNotEmpty())
        {
            val actColor = ColorStateList.valueOf(Color.parseColor(actionsColor))

            btnPlus.backgroundTintList = actColor
            btnMinus.backgroundTintList = actColor
            btnMulti.backgroundTintList = actColor
            btnDivision.backgroundTintList = actColor
            btnExp.backgroundTintList = actColor
            btnPi.backgroundTintList = actColor
            btnSquareRoot.backgroundTintList = actColor
            btnBrackets.backgroundTintList = actColor
            btnSin.backgroundTintList = actColor
            btnCos.backgroundTintList = actColor
            btnTan.backgroundTintList = actColor
            btnE.backgroundTintList = actColor
            btnLg.backgroundTintList = actColor
            btnLn.backgroundTintList = actColor
            btnLog2.backgroundTintList = actColor
        }


        if (acColor.isNotEmpty())
        {
            btnClearEdt.backgroundTintList = ColorStateList.valueOf(Color.parseColor(acColor))
        }
        if (equalColor.isNotEmpty())
        {
            val eColor = ColorStateList.valueOf(Color.parseColor(equalColor))
            btnBuild.backgroundTintList = eColor
            btnX.backgroundTintList = eColor
        }

        if (textColor.isNotEmpty()) setButtonsTextColor(textColor)

        if (shape.isNotEmpty())
        {
            when (shape)
            {
                SHAPE_ROUNDED ->
                {
                    setButtonsRounded()
                }
                SHAPE_RECTANGLE ->
                {
                    setButtonsRectangle()
                }
                SHAPE_CIRCLE ->
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
        btnBackSpace.setTextColor(tColor)
        btnDot.setTextColor(tColor)

        btnX.setTextColor(tColor)
        btnKeyboard.setTextColor(tColor)

        btnPlus.setTextColor(tColor)
        btnMinus.setTextColor(tColor)
        btnMulti.setTextColor(tColor)
        btnDivision.setTextColor(tColor)
        btnExp.setTextColor(tColor)
        btnPi.setTextColor(tColor)
        btnSquareRoot.setTextColor(tColor)
        btnBrackets.setTextColor(tColor)
        btnSin.setTextColor(tColor)
        btnCos.setTextColor(tColor)
        btnTan.setTextColor(tColor)
        btnE.setTextColor(tColor)
        btnLg.setTextColor(tColor)
        btnLn.setTextColor(tColor)
        btnLog2.setTextColor(tColor)

        btnZeroKeyboard.setTextColor(tColor)
        btnOneKeyboard.setTextColor(tColor)
        btnTwoKeyboard.setTextColor(tColor)
        btnThreeKeyboard.setTextColor(tColor)
        btnFourKeyboard.setTextColor(tColor)
        btnFiveKeyboard.setTextColor(tColor)
        btnSixKeyboard.setTextColor(tColor)
        btnSevenKeyboard.setTextColor(tColor)
        btnEightKeyboard.setTextColor(tColor)
        btnNineKeyboard.setTextColor(tColor)
        btnDotKeyboard.setTextColor(tColor)
        btnMinusKeyboard.setTextColor(tColor)
        btnBackspaceKeyboard.setTextColor(tColor)


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
        btnKeyboard.background = circle


        btnPlus.background = circle
        btnMinus.background = circle
        btnMulti.background = circle
        btnDivision.background = circle
        btnExp.background = circle
        btnPi.background = circle
        btnSquareRoot.background =
            circle
        btnBrackets.background = circle
        btnSin.background = circle
        btnCos.background = circle
        btnTan.background = circle
        btnE.background = circle
        btnLg.background = circle
        btnLn.background = circle
        btnLog2.background = circle
        btnX.background = circle
        btnBuild.background = circle
        btnClearEdt.background = circle

        btnZeroKeyboard.background = circle
        btnOneKeyboard.background = circle
        btnTwoKeyboard.background = circle
        btnThreeKeyboard.background = circle
        btnFourKeyboard.background = circle
        btnFiveKeyboard.background = circle
        btnSixKeyboard.background = circle
        btnSevenKeyboard.background = circle
        btnEightKeyboard.background = circle
        btnNineKeyboard.background = circle
        btnDotKeyboard.background = circle
        btnMinusKeyboard.background = circle
        btnBackspaceKeyboard.background = circle

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
        btnKeyboard.background = rectangle

        btnPlus.background = rectangle
        btnMinus.background = rectangle
        btnMulti.background = rectangle
        btnDivision.background = rectangle
        btnExp.background = rectangle
        btnPi.background = rectangle
        btnSquareRoot.background =
            rectangle
        btnBrackets.background = rectangle
        btnSin.background = rectangle
        btnCos.background = rectangle
        btnTan.background = rectangle
        btnE.background = rectangle
        btnLg.background = rectangle
        btnLn.background = rectangle
        btnLog2.background = rectangle
        btnX.background = rectangle
        btnBuild.background = rectangle
        btnClearEdt.background = rectangle


        btnZeroKeyboard.background = rectangle
        btnOneKeyboard.background = rectangle
        btnTwoKeyboard.background = rectangle
        btnThreeKeyboard.background = rectangle
        btnFourKeyboard.background = rectangle
        btnFiveKeyboard.background = rectangle
        btnSixKeyboard.background = rectangle
        btnSevenKeyboard.background = rectangle
        btnEightKeyboard.background = rectangle
        btnNineKeyboard.background = rectangle
        btnDotKeyboard.background = rectangle
        btnBackspaceKeyboard.background = rectangle
        btnMinusKeyboard.background = rectangle
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
        btnKeyboard.background = rounded


        btnPlus.background = rounded
        btnMinus.background = rounded
        btnMulti.background = rounded
        btnDivision.background = rounded
        btnExp.background = rounded
        btnPi.background = rounded
        btnSquareRoot.background =
            rounded
        btnBrackets.background = rounded
        btnSin.background = rounded
        btnCos.background = rounded
        btnTan.background = rounded
        btnE.background = rounded
        btnLg.background = rounded
        btnLn.background = rounded
        btnLog2.background = rounded
        btnX.background = rounded
        btnBuild.background = rounded
        btnClearEdt.background = rounded


        btnZeroKeyboard.background = rounded
        btnOneKeyboard.background = rounded
        btnTwoKeyboard.background = rounded
        btnThreeKeyboard.background = rounded
        btnFourKeyboard.background = rounded
        btnFiveKeyboard.background = rounded
        btnSixKeyboard.background = rounded
        btnSevenKeyboard.background = rounded
        btnEightKeyboard.background = rounded
        btnNineKeyboard.background = rounded
        btnDotKeyboard.background = rounded
        btnMinusKeyboard.background = rounded
        btnBackspaceKeyboard.background = rounded

    }
}
