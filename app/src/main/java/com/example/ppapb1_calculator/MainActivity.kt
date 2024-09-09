package com.example.ppapb1_calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ppapb1_calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private var input = ""
    private var operator = ""
    private var firstNumber = ""
    private var secondNumber = ""
    private var isNewOp = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val numberButtons = listOf(
                button0, button1, button2, button3, button4,
                button5, button6, button7, button8, button9
            )

            numberButtons.forEach { button ->
                button.setOnClickListener { numberClick(button.text.toString()) }
            }

            buttonPlus.setOnClickListener { operatorClick("+") }
            buttonMinus.setOnClickListener { operatorClick("-") }
            buttonMultiply.setOnClickListener { operatorClick("*") }
            buttonDivide.setOnClickListener { operatorClick("/") }
            buttonPercent.setOnClickListener { operatorClick("%") }

            buttonAc.setOnClickListener { clearClick() }
            buttonEquals.setOnClickListener { equalsClick() }
            buttonDot.setOnClickListener { dotClick() }
        }
    }

    private fun numberClick(number: String) {
        if (isNewOp) {
            input = ""
            isNewOp = false
        }
        input += number
        binding.result.text = input

        if (operator.isEmpty()) {
            firstNumber = input
        } else {
            secondNumber = input
        }

        updateExpression()
    }

    private fun operatorClick(op: String) {
        if (firstNumber.isNotEmpty() && secondNumber.isEmpty()) {
            operator = op
            input = ""
            isNewOp = false
            updateExpression()
        }
    }

    private fun equalsClick() {
        if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
            val result: Double? = when (operator) {
                "%" -> firstNumber.toDouble() / 100 * secondNumber.toDouble()
                "+" -> firstNumber.toDouble() + secondNumber.toDouble()
                "-" -> firstNumber.toDouble() - secondNumber.toDouble()
                "*" -> firstNumber.toDouble() * secondNumber.toDouble()
                "/" -> if (secondNumber.toDouble() != 0.0) {
                    firstNumber.toDouble() / secondNumber.toDouble()
                } else {
                    null
                }
                else -> null
            }

            binding.result.text = result?.toString() ?: "Error"
            input = result?.toString() ?: ""
            resetOperation()
        }
    }

    private fun clearClick() {
        resetOperation()
        binding.result.text = "0"
        binding.expression.text = ""
    }

    private fun dotClick() {
        if (!input.contains(".")) {
            input += "."
            binding.result.text = input
        }
    }

    private fun resetOperation() {
        input = ""
        firstNumber = ""
        secondNumber = ""
        operator = ""
        isNewOp = true
    }

    private fun updateExpression() {
        binding.expression.text = "$firstNumber $operator $secondNumber"
    }
}
