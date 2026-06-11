package com.itsxlord.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private lateinit var tvHistory: TextView
    private var firstValue: Double = Double.NaN
    private var currentInput: String = ""
    private var operator: String? = null
    private var isNewOperation: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        tvHistory = findViewById(R.id.tvHistory)

        val digitButtons = listOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9", R.id.btnDot to "."
        )

        for ((id, digit) in digitButtons) {
            findViewById<Button>(id).setOnClickListener {
                if (isNewOperation) {
                    currentInput = ""
                    isNewOperation = false
                }
                
                if (digit == "." && currentInput.contains(".")) return@setOnClickListener
                if (digit == "." && currentInput.isEmpty()) {
                    currentInput = "0."
                } else if (digit == "0" && currentInput == "0") {
                    return@setOnClickListener
                } else if (currentInput == "0" && digit != ".") {
                    currentInput = digit
                } else {
                    currentInput += digit
                }
                
                tvResult.text = currentInput
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { prepareOperator("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { prepareOperator("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { prepareOperator("×") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { prepareOperator("÷") }

        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener {
            val value = currentInput.toDoubleOrNull() ?: tvResult.text.toString().toDoubleOrNull()
            if (value != null && !value.isNaN()) {
                val toggled = value * -1
                val resultStr = formatResult(toggled)
                currentInput = resultStr
                tvResult.text = resultStr
            }
        }

        findViewById<Button>(R.id.btnPercent).setOnClickListener {
            val value = currentInput.toDoubleOrNull() ?: tvResult.text.toString().toDoubleOrNull()
            if (value != null && !value.isNaN()) {
                val percent = value / 100
                val resultStr = formatResult(percent)
                currentInput = resultStr
                tvResult.text = resultStr
                tvHistory.text = "" 
            }
        }

        findViewById<Button>(R.id.btnAC).setOnClickListener {
            currentInput = ""
            firstValue = Double.NaN
            operator = null
            isNewOperation = true
            tvResult.text = "0"
            tvHistory.text = ""
        }

        findViewById<Button>(R.id.btnEq).setOnClickListener {
            if (operator != null && !firstValue.isNaN()) {
                val secondValue = currentInput.toDoubleOrNull() ?: firstValue
                val result = performCalculation(firstValue, secondValue, operator!!)
                
                if (result.isNaN() || result.isInfinite()) {
                    tvResult.text = "Error"
                    tvHistory.text = ""
                    currentInput = ""
                    firstValue = Double.NaN
                } else {
                    tvHistory.text = "${formatResult(firstValue)} $operator ${formatResult(secondValue)} ="
                    val resultStr = formatResult(result)
                    tvResult.text = resultStr
                    currentInput = resultStr
                    firstValue = result
                }
                operator = null
                isNewOperation = true
            }
        }
    }

    private fun prepareOperator(op: String) {
        val inputValue = currentInput.toDoubleOrNull()
        
        if (inputValue != null && !isNewOperation) {
            if (!firstValue.isNaN() && operator != null) {
                firstValue = performCalculation(firstValue, inputValue, operator!!)
                tvResult.text = formatResult(firstValue)
            } else {
                firstValue = inputValue
            }
        } else if (firstValue.isNaN()) {
            firstValue = inputValue ?: 0.0
        }
        
        operator = op
        tvHistory.text = "${formatResult(firstValue)} $op"
        isNewOperation = true
    }

    private fun performCalculation(a: Double, b: Double, op: String): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "×" -> a * b
            "÷" -> if (b != 0.0) a / b else Double.NaN
            else -> b
        }
    }

    private fun formatResult(result: Double): String {
        return if (result % 1 == 0.0) {
            if (result > Long.MAX_VALUE || result < Long.MIN_VALUE) result.toString()
            else result.toLong().toString()
        } else {
            result.toString()
        }
    }
}
