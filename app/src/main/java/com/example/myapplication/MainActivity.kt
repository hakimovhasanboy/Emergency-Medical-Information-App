package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var cmToM = true

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val outputTextView = binding.outputTextView
        val outputUnitTextView = binding.outputUnitTextView
        val inputEditText = binding.inputEditText
        val inputUnitTextView = binding.inputUnitTextView
        val swapImageButton = binding.swapImageButton

        var inputNumber: Int = 0

        inputEditText.addTextChangedListener {text ->
            inputNumber = if (text.isNullOrEmpty()) {
                0
            } else {
                text.toString().toInt()
            }

            if (cmToM) {
                outputTextView.text = inputNumber.times(0.01).toString()
            } else {
                outputTextView.text = inputNumber.times(100).toString()
            }

            swapImageButton.setOnClickListener {
                cmToM = cmToM.not()
                if (cmToM) {
                    inputUnitTextView.text = "cm"
                    outputUnitTextView.text = "m"
                    outputTextView.text = inputNumber.times(0.01).toString()
                } else {
                    inputUnitTextView.text = "m"
                    outputUnitTextView.text = "cm"
                    outputTextView.text = inputNumber.times(100).toString()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("cmToM", cmToM)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        cmToM = savedInstanceState.getBoolean("cmToM")
        Log.d("cmToM", cmToM.toString())
        binding.inputUnitTextView.text = if (cmToM) "cm" else "m"
        binding.outputUnitTextView.text = if (cmToM) "m" else "cm"
        super.onRestoreInstanceState(savedInstanceState)
    }


}
