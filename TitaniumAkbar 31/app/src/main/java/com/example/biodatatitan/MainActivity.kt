package com.example.biodatatitan

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNama: EditText
    private lateinit var editTextAlamat: EditText
    private lateinit var editTextTanggalLahir: EditText
    private lateinit var radioGroupJenisKelamin: RadioGroup
    private lateinit var spinnerAgama: Spinner
    private lateinit var buttonSimpan: Button

    // SharedPreferences untuk menyimpan data
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "BiodataPref"
    private val KEY_NAMA = "nama"
    private val KEY_ALAMAT = "alamat"
    private val KEY_TANGGAL_LAHIR = "tanggal_lahir"
    private val KEY_JENIS_KELAMIN_ID = "jenis_kelamin_id"
    private val KEY_AGAMA_POS = "agama_pos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Menghubungkan variabel dengan komponen di layout
        inisialisasiKomponen()
        setupSpinner()
        setupDatePicker()

        buttonSimpan.setOnClickListener {
            if (validasiInput()) {
                simpanDanTampilkanData()
            }
        }

        // Muat data yang tersimpan saat activity dibuat
        muatDataTersimpan()
    }

    private fun inisialisasiKomponen() {
        editTextNama = findViewById(R.id.editTextNama)
        editTextAlamat = findViewById(R.id.editTextAlamat)
        editTextTanggalLahir = findViewById(R.id.editTextTanggalLahir)
        radioGroupJenisKelamin = findViewById(R.id.radioGroupJenisKelamin)
        spinnerAgama = findViewById(R.id.spinnerAgama)
        buttonSimpan = findViewById(R.id.buttonSimpan)
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.agama_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerAgama.adapter = adapter
        }
    }

    private fun setupDatePicker() {
        editTextTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editTextTanggalLahir.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun validasiInput(): Boolean {
        if (TextUtils.isEmpty(editTextNama.text.toString())) {
            Toast.makeText(this, "Nama lengkap harus diisi!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(editTextAlamat.text.toString())) {
            Toast.makeText(this, "Alamat harus diisi!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(editTextTanggalLahir.text.toString())) {
            Toast.makeText(this, "Tanggal lahir harus diisi!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (radioGroupJenisKelamin.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Jenis kelamin harus dipilih!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun simpanDanTampilkanData() {
        // Ambil data dari form
        val nama = editTextNama.text.toString()
        val alamat = editTextAlamat.text.toString()
        val tanggalLahir = editTextTanggalLahir.text.toString()

        val selectedJenisKelaminId = radioGroupJenisKelamin.checkedRadioButtonId
        val radioButtonJenisKelamin = findViewById<RadioButton>(selectedJenisKelaminId)
        val jenisKelamin = radioButtonJenisKelamin.text.toString()

        val agama = spinnerAgama.selectedItem.toString()
        val agamaPosition = spinnerAgama.selectedItemPosition

        // 1. Simpan data ke SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAMA, nama)
        editor.putString(KEY_ALAMAT, alamat)
        editor.putString(KEY_TANGGAL_LAHIR, tanggalLahir)
        editor.putInt(KEY_JENIS_KELAMIN_ID, selectedJenisKelaminId)
        editor.putInt(KEY_AGAMA_POS, agamaPosition)
        editor.apply() // .apply() berjalan di background, lebih efisien

        // 2. Kirim data ke ResultActivity dan mulai activity
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("EXTRA_NAMA", nama)
            putExtra("EXTRA_ALAMAT", alamat)
            putExtra("EXTRA_TANGGAL_LAHIR", tanggalLahir)
            putExtra("EXTRA_JENIS_KELAMIN", jenisKelamin)
            putExtra("EXTRA_AGAMA", agama)
        }
        startActivity(intent)
    }

    private fun muatDataTersimpan() {
        val nama = sharedPreferences.getString(KEY_NAMA, "")
        val alamat = sharedPreferences.getString(KEY_ALAMAT, "")
        val tanggalLahir = sharedPreferences.getString(KEY_TANGGAL_LAHIR, "")
        val jenisKelaminId = sharedPreferences.getInt(KEY_JENIS_KELAMIN_ID, -1)
        val agamaPos = sharedPreferences.getInt(KEY_AGAMA_POS, 0)

        editTextNama.setText(nama)
        editTextAlamat.setText(alamat)
        editTextTanggalLahir.setText(tanggalLahir)
        if (jenisKelaminId != -1) {
            radioGroupJenisKelamin.check(jenisKelaminId)
        }
        spinnerAgama.setSelection(agamaPos)
    }
}