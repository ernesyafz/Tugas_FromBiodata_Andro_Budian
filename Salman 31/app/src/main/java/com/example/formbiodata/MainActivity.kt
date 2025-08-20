package com.example.formbiodata

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etTanggal: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var spAgama: Spinner
    private lateinit var btnSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hubungkan ke XML
        etNama = findViewById(R.id.etNama)
        etAlamat = findViewById(R.id.etAlamat)
        etTanggal = findViewById(R.id.etTanggal)
        rgGender = findViewById(R.id.rgGender)
        spAgama = findViewById(R.id.spAgama)
        btnSimpan = findViewById(R.id.btnSimpan)

        // Data Spinner
        val agamaList = arrayOf("Islam", "Kristen", "Hindu", "Budha", "Konghucu")
        spAgama.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, agamaList)

        // DatePicker untuk tanggal lahir
        etTanggal.setOnClickListener {
            val c = Calendar.getInstance()
            val tahun = c.get(Calendar.YEAR)
            val bulan = c.get(Calendar.MONTH)
            val hari = c.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    etTanggal.setText("$dayOfMonth/${month+1}/$year")
                }, tahun, bulan, hari)
            datePicker.show()
        }

        // Tombol Simpan
        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString()
            val alamat = etAlamat.text.toString()
            val tanggal = etTanggal.text.toString()
            val genderId = rgGender.checkedRadioButtonId
            val gender = if (genderId != -1) findViewById<RadioButton>(genderId).text.toString() else ""
            val agama = spAgama.selectedItem.toString()

            // Validasi
            if (nama.isEmpty() || alamat.isEmpty() || tanggal.isEmpty() || gender.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show()
            } else {
                // Kirim ke ResultActivity
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("nama", nama)
                intent.putExtra("alamat", alamat)
                intent.putExtra("tanggal", tanggal)
                intent.putExtra("gender", gender)
                intent.putExtra("agama", agama)
                startActivity(intent)
            }
        }
    }
}
