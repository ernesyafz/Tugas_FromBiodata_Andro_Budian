package com.example.formbiodata

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etTglLahir: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var spinnerAgama: Spinner
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNama = findViewById(R.id.etNama)
        etAlamat = findViewById(R.id.etAlamat)
        etTglLahir = findViewById(R.id.etTglLahir)
        rgGender = findViewById(R.id.rgGender)
        spinnerAgama = findViewById(R.id.spinnerAgama)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Setup Spinner Agama
        val agamaList = listOf("Pilih Agama", "Islam", "Kristen", "Katolik", "Hindu", "Budha", "Lainnya")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, agamaList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAgama.adapter = adapter

        // DatePicker untuk Tanggal Lahir
        etTglLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this,
                { _, y, m, d ->
                    etTglLahir.setText(String.format("%02d/%02d/%04d", d, m+1, y))
                }, year, month, day)
            datePicker.show()
        }

        btnSubmit.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val tglLahir = etTglLahir.text.toString().trim()
            val genderId = rgGender.checkedRadioButtonId
            val gender = if (genderId != -1) findViewById<RadioButton>(genderId).text.toString() else ""
            val agama = spinnerAgama.selectedItem.toString()

            if(nama.isEmpty() || alamat.isEmpty() || tglLahir.isEmpty() || gender.isEmpty() || agama=="Pilih Agama"){
                Toast.makeText(this, "Lengkapi semua data!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("nama", nama)
                intent.putExtra("alamat", alamat)
                intent.putExtra("tglLahir", tglLahir)
                intent.putExtra("gender", gender)
                intent.putExtra("agama", agama)
                startActivity(intent)
            }
        }
    }
}
