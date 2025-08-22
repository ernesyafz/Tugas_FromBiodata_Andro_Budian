package com.example.rakha_27

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etHouse: EditText
    private lateinit var etBirthDate: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var spinnerReligion: Spinner
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etFullName = findViewById(R.id.etFullName)
        etHouse = findViewById(R.id.etHouse)
        etBirthDate = findViewById(R.id.etBirthDate)
        rgGender = findViewById(R.id.rgGender)
        spinnerReligion = findViewById(R.id.spinnerReligion)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Setup spinner using the string-array from strings.xml
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.religion,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerReligion.adapter = adapter

        // Date picker for birth date
        etBirthDate.setOnClickListener {
            showDatePicker()
        }

        btnSubmit.setOnClickListener {
            submitForm()
        }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, y, m, d ->
            val pickedCal = Calendar.getInstance()
            pickedCal.set(y, m, d)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            etBirthDate.setText(sdf.format(pickedCal.time))
        }, year, month, day)

        dpd.show()
    }

    private fun submitForm() {
        val fullName = etFullName.text.toString().trim()
        val house = etHouse.text.toString().trim()
        val birthDate = etBirthDate.text.toString().trim()
        val selectedGenderId = rgGender.checkedRadioButtonId
        val religionPosition = spinnerReligion.selectedItemPosition

        // Validation
        if (fullName.isEmpty() || house.isEmpty() || birthDate.isEmpty()
            || selectedGenderId == -1 || religionPosition == 0
        ) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val gender = findViewById<RadioButton>(selectedGenderId).text.toString()
        val religion = spinnerReligion.selectedItem.toString()

        // Pass data to SecondActivity
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("fullName", fullName)
            putExtra("house", house)
            putExtra("birthDate", birthDate)
            putExtra("gender", gender)
            putExtra("religion", religion)
        }
        startActivity(intent)
    }
}