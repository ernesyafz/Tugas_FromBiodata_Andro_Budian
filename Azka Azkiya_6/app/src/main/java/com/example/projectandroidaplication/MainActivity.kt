package com.example.projectandroidaplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var spAgama: Spinner
    private lateinit var btnSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNama = findViewById(R.id.etNama)
        etAlamat = findViewById(R.id.etAlamat)
        etTanggalLahir = findViewById(R.id.etTanggalLahir)
        rgGender = findViewById(R.id.rgGender)
        spAgama = findViewById(R.id.spAgama)
        btnSimpan = findViewById(R.id.btnSimpan)

        // Spinner Agama dari resources (strings.xml)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.agama_items,
            android.R.layout.simple_spinner_dropdown_item
        )
        spAgama.adapter = adapter

        // DatePicker untuk tanggal lahir
        etTanggalLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                val dd = "%02d".format(d)
                val mm = "%02d".format(m + 1)
                etTanggalLahir.setText("$dd/$mm/$y")
            }, year, month, day)
            datePicker.show()
        }

        // Tombol simpan
        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val tglLahir = etTanggalLahir.text.toString().trim()
            val selectedGenderId = rgGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1)
                findViewById<RadioButton>(selectedGenderId).text.toString()
            else
                ""
            val agama = spAgama.selectedItem?.toString()?.trim().orEmpty()

            // Validasi
            if (nama.isEmpty() || alamat.isEmpty() || tglLahir.isEmpty() || gender.isEmpty() || agama.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Navigasi ke ResultActivity + kirim data
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("NAMA", nama)
                putExtra("ALAMAT", alamat)
                putExtra("TGL", tglLahir)
                putExtra("GENDER", gender)
                putExtra("AGAMA", agama)
            }
            startActivity(intent)
        }
    }
}