package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun numberClicked(view: View) {
        val numberString = (view as? Button)?.text.toString()
        val numberText = if (operatorText.isEmpty()) firstNumberText else secondNumberText

        numberText.append(numberString)
        updateEquationTextView()
    }
    fun clearClicked(view: View) {
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()
        binding.resultTextView.text = ""
        updateEquationTextView()
    }
    fun equalClicked(view: View) {
        if (firstNumberText.isEmpty() || operatorText.isEmpty() || secondNumberText.isEmpty()) {
            Toast.makeText(this, "Calculate is not true.", Toast.LENGTH_SHORT).show()
            return
        }

        val firstNumber = firstNumberText.toString().toInt()
        val secondNumber = secondNumberText.toString().toInt()

       /* val result =  if(operatorText.toString() == "+") {
            firstNumber + secondNumber
        }else if (operatorText.toString() == "-") {
            firstNumber - secondNumber
        }
        else {
            Toast.makeText(this, "Calculate is not true. Error", Toast.LENGTH_SHORT).show()
            return
        }*/

        val result = when(operatorText.toString()) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            else -> {
                return
            }
        }

        binding.resultTextView.text = result.toString()

    }
    fun operatorClicked(view: View) {
        val operatorString = (view as? Button)?.text?.toString() ?: ""

        if (firstNumberText.isEmpty()) {
            Toast.makeText(this, "Please input first number.", Toast.LENGTH_SHORT).show()
            return
        }

        if (secondNumberText.isNotEmpty()) {
            Toast.makeText(this, "Can only one operator calculate.", Toast.LENGTH_SHORT).show()
            return
        }

        operatorText.append(operatorString)



    }

    private fun updateEquationTextView() {
        binding.equationTextView.text = "$firstNumberText $operatorText $secondNumberText"
    }
}

















/*private lateinit var resultTextView: TextView
    private var currentNumber = ""
    private var operation = ""
    private var firstNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        val numberButtons = listOf(
            findViewById<Button>(R.id.button0),
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7),
            findViewById<Button>(R.id.button8),
            findViewById<Button>(R.id.button9)
        )

        for (button in numberButtons) {
            button.setOnClickListener {
                currentNumber += button.text
                resultTextView.text = currentNumber
            }
        }

        findViewById<Button>(R.id.buttonClear).setOnClickListener {
            currentNumber = ""
            firstNumber = ""
            operation = ""
            resultTextView.text = "0"
        }

        findViewById<Button>(R.id.buttonPlus).setOnClickListener { setOperation("+") }
        findViewById<Button>(R.id.buttonMinus).setOnClickListener { setOperation("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { setOperation("*") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { setOperation("/") }

        findViewById<Button>(R.id.buttonEquals).setOnClickListener {
            val secondNumber = currentNumber.toDoubleOrNull()
            if (firstNumber.isNotEmpty() && secondNumber != null) {
                val result = calculateResult(firstNumber.toDouble(), secondNumber, operation)
                resultTextView.text = result.toString()
                currentNumber = result.toString()
                firstNumber = ""
                operation = ""
            }
        }
    }

    private fun setOperation(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber
            currentNumber = ""
            operation = op
        }
    }

    private fun calculateResult(first: Double, second: Double, op: String): Double {
        return when (op) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> first / second
            else -> 0.0
        }
    }*/

