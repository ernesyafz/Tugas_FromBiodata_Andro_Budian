package com.example.gatauu

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var spinnerAgama: Spinner
    private lateinit var btnSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi komponen UI
        etNama = findViewById(R.id.et_nama)
        etAlamat = findViewById(R.id.et_alamat)
        etTanggalLahir = findViewById(R.id.et_tanggal_lahir)
        rgJenisKelamin = findViewById(R.id.rg_jenis_kelamin)
        spinnerAgama = findViewById(R.id.spinner_agama)
        btnSimpan = findViewById(R.id.btn_simpan)

        // Menyiapkan data untuk Spinner
        val agamaList = arrayOf("Pilih Agama", "Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, agamaList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAgama.adapter = adapter

        // Menangani klik pada EditText Tanggal Lahir untuk memunculkan DatePickerDialog
        etTanggalLahir.setOnClickListener {
            showDatePickerDialog()
        }

        // Menangani klik pada tombol Simpan
        btnSimpan.setOnClickListener {
            validateAndShowResult()
        }
    }

    private fun showDatePickerDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        try {
            val datePickerDialog = DatePickerDialog(this, { _, y, m, d ->
                val selectedDate = "$d/${m + 1}/$y"
                etTanggalLahir.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal menampilkan Date Picker.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateAndShowResult() {
        val nama = etNama.text?.toString()?.trim()
        val alamat = etAlamat.text?.toString()?.trim()
        val tanggalLahir = etTanggalLahir.text?.toString()?.trim()
        val selectedJenisKelaminId = rgJenisKelamin.checkedRadioButtonId
        val agama = spinnerAgama.selectedItem.toString()

        if (nama.isNullOrEmpty() || alamat.isNullOrEmpty() || tanggalLahir.isNullOrEmpty() || selectedJenisKelaminId == -1 || agama == "Pilih Agama") {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedRadioButton: RadioButton? = try {
            findViewById(selectedJenisKelaminId)
        } catch (e: Exception) {
            null
        }

        if (selectedRadioButton == null) {
            Toast.makeText(this, "Pilih jenis kelamin terlebih dahulu.", Toast.LENGTH_SHORT).show()
            return
        }
        val jenisKelamin = selectedRadioButton.text.toString()

        val biodata = "Nama: $nama\n" +
                "Alamat: $alamat\n" +
                "Tanggal Lahir: $tanggalLahir\n" +
                "Jenis Kelamin: $jenisKelamin\n" +
                "Agama: $agama"

        AlertDialog.Builder(this)
            .setTitle("Hasil Biodata")
            .setMessage(biodata)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "Biodata berhasil disimpan!", Toast.LENGTH_SHORT).show()
            }
            .show()
    }
}