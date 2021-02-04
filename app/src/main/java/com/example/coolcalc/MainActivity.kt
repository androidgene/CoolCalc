package com.example.coolcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private lateinit var entered: TextView
    private lateinit var current: TextView
    private var lastNumeric: Boolean = false
    private var error: Boolean = false
    private var lastDecPoint: Boolean = false
    private var lastTotal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v("Start", "onCreate")
        current = findViewById(R.id.current_txt)
        Log.v("Start","current defined")
        entered = findViewById(R.id.entered_txt)
        Log.v("Start","entered defined")

        val buttonZero = findViewById<Button>(R.id.button_0)
        buttonZero.setOnClickListener {
            onDigit("0")
        }
        val button1 = findViewById<Button>(R.id.button_1)
            button1.setOnClickListener {
            onDigit("1")
        }
        val button2 = findViewById<Button>(R.id.button_2)
            button2.setOnClickListener {
                onDigit("2")
        }
        val button3 = findViewById<Button>(R.id.button_3)
        button3.setOnClickListener {
            onDigit("3")
        }
        val button4 = findViewById<Button>(R.id.button_4)
        button4.setOnClickListener {
            onDigit("4")
        }
        val button5 = findViewById<Button>(R.id.button_5)
        button5.setOnClickListener {
            onDigit("5")
        }
        val button6 = findViewById<Button>(R.id.button_6)
        button6.setOnClickListener {
            onDigit("6")
        }
        val button7 = findViewById<Button>(R.id.button_7)
        button7.setOnClickListener {
            onDigit("7")
        }
        val button8 = findViewById<Button>(R.id.button_8)
        button8.setOnClickListener {
            onDigit("8")
        }
        val button9 = findViewById<Button>(R.id.button_9)
        button9.setOnClickListener {
            onDigit("9")
        }
        val buttonPlus = findViewById<Button>(R.id.button_add)
        buttonPlus.setOnClickListener {
            onOperator("+")
        }
        val buttonSub = findViewById<Button>(R.id.button_subtract)
        buttonSub.setOnClickListener {
            onOperator("-")
        }
        val buttonMult = findViewById<Button>(R.id.button_multiply)
        buttonMult.setOnClickListener {
            onOperator("*")
        }
        val buttonDiv = findViewById<Button>(R.id.button_divide)
        buttonDiv.setOnClickListener {
            onOperator("/")
        }
        val buttonTotal = findViewById<Button>(R.id.button_total)
        buttonTotal.setOnClickListener {
            onEqual()
        }
        val buttonClear = findViewById<Button>(R.id.button_clear)
        buttonClear.setOnClickListener {
            clearEntry()
        }
        val buttonDecPoint = findViewById<Button>(R.id.button_decimal)
        buttonDecPoint.setOnClickListener {
            onDecimalPoint(".")
        }
    }

    private fun onDigit(number: String) {
        if (lastTotal) {
            clearEntry()
            lastTotal = false
        }
        if (error) {
            entered.text = number
            error = false
        } else {
            entered.append(number)
        }
        lastNumeric = true
        lastDecPoint = false
        lastTotal = false
        /*calculateTotal(entered.text.toString()) */
    }

    private fun onDecimalPoint(point: String) {
        if (lastTotal) {
            clearEntry()
            lastTotal = false
        }
        if (lastNumeric && !error && !lastDecPoint) {
            entered.append(point)
        }

        lastNumeric = false
        lastDecPoint = true
        lastTotal = false
        /*calculateTotal(entered.text.toString())*/
    }

    private fun onOperator(button: String) {
        if (lastTotal) {
            clearEntry()
            lastTotal = false
        }
        calculateTotal(entered.text.toString())
        /*if (lastNumeric && !error) {*/
        if (!error) {
            entered.append(button)
        }
        lastNumeric = false
        lastDecPoint = false
        lastTotal = false
        calculateTotal(entered.text.toString())
    }

    private fun onEqual(){
        if (lastNumeric && !error) {
            calculateTotal(entered.text.toString())
            entered.text = current.text
            current.text = ""
            lastTotal = true
            lastNumeric = false
            lastDecPoint = false
        }
    }

    private fun clearEntry(){
        this.entered.text = ""
        this.current.text = ""
        lastDecPoint = false
        lastNumeric = false
        lastTotal = false
        error = false
    }

    private fun calculateTotal(input: String){
        if (lastNumeric && !error) {
            Log.v("Calc", " $input")
            val expression = ExpressionBuilder(input).build()
            try {
                val result = expression.evaluate()
                current.text = result.toString()
                lastDecPoint = true
            } catch (err: ArithmeticException) {
                entered.text = "Error"
                error = true
                lastNumeric = false
            }
        }
    }
}