package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        binding.emergencyLayer.setOnClickListener {
            val intent = with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.emergencyValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataUiUpdate()
    }

    private fun getDataUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.nameValueTextView.text = getString(NAME, "Undecided-미정")
            binding.dateOfBirthValueTextView.text = getString(BIRTHDATE, "Undecided-미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "Undecided-미정")
            binding.emergencyValueTextView.text = getString(EMERGENCY, "Undecided-미정")

            val warning = getString(WARNING, "")
            binding.showNotesTextView.isVisible = warning.isNullOrEmpty().not()
            binding.showNotesValueTextView.isVisible = warning.isNullOrEmpty().not()

            if (!warning.isNullOrEmpty()) {
                binding.showNotesValueTextView.text = warning
            }

            /*
            val warning = getString(WARNING, "")
            if (warning.isNullOrEmpty()) {
                binding.showNotesTextView.isVisible = false
                binding.showNotesValueTextView.isVisible = false
            } else {
                binding.showNotesTextView.isVisible = true
                binding.showNotesValueTextView.isVisible = true
                binding.showNotesValueTextView.text = warning
            }
            */



        }
    }

    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()) {
            clear()
            apply()
            getDataUiUpdate()
        }
        Toast.makeText(this, "초기화를 완료했습니다", Toast.LENGTH_SHORT).show()
    }

}
