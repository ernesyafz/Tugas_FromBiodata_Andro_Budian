package com.example.apkbiodata // Ganti dengan package name kamu

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.example.apkbiodata.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("BIODATA_PREF", Context.MODE_PRIVATE)

        setupSpinner()
        setupDatePicker()

        // Muat data yang tersimpan saat activity dibuat
        loadSavedData()

        binding.btnSimpan.setOnClickListener {
            if (validateInput()) {
                saveAndShowData()
            }
        }
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.agama_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAgama.adapter = adapter
        }
    }

    private fun setupDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        binding.etTanggalLahir.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd MMMM yyyy" // contoh: 17 Agustus 2024
        val sdf = SimpleDateFormat(myFormat, Locale("id", "ID"))
        binding.etTanggalLahir.setText(sdf.format(calendar.time))
    }

    private fun validateInput(): Boolean {
        val nama = binding.etNamaLengkap.text.toString().trim()
        val tanggalLahir = binding.etTanggalLahir.text.toString().trim()
        val alamat = binding.etAlamat.text.toString().trim()
        val jenisKelaminId = binding.rgJenisKelamin.checkedRadioButtonId

        if (nama.isEmpty() || tanggalLahir.isEmpty() || alamat.isEmpty() || jenisKelaminId == -1) {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun saveAndShowData() {
        val nama = binding.etNamaLengkap.text.toString().trim()
        val tanggalLahir = binding.etTanggalLahir.text.toString().trim()
        val alamat = binding.etAlamat.text.toString().trim()
        val selectedJenisKelaminId = binding.rgJenisKelamin.checkedRadioButtonId
        val jenisKelamin = findViewById<RadioButton>(selectedJenisKelaminId).text.toString()
        val agama = binding.spinnerAgama.selectedItem.toString()
        val agamaPosition = binding.spinnerAgama.selectedItemPosition

        // 1. Simpan data ke SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("NAMA", nama)
        editor.putString("TANGGAL_LAHIR", tanggalLahir)
        editor.putString("ALAMAT", alamat)
        editor.putString("JENIS_KELAMIN", jenisKelamin)
        editor.putInt("AGAMA_POS", agamaPosition)
        editor.apply() // Simpan secara asynchronous

        Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()

        // 2. Kirim data dan pindah ke ResultActivity
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("EXTRA_NAMA", nama)
            putExtra("EXTRA_TANGGAL_LAHIR", tanggalLahir)
            putExtra("EXTRA_ALAMAT", alamat)
            putExtra("EXTRA_JENIS_KELAMIN", jenisKelamin)
            putExtra("EXTRA_AGAMA", agama)
        }
        startActivity(intent)
    }

    private fun loadSavedData() {
        val savedNama = sharedPreferences.getString("NAMA", "")
        val savedTglLahir = sharedPreferences.getString("TANGGAL_LAHIR", "")
        val savedAlamat = sharedPreferences.getString("ALAMAT", "")
        val savedJenisKelamin = sharedPreferences.getString("JENIS_KELAMIN", "")
        val savedAgamaPos = sharedPreferences.getInt("AGAMA_POS", 0)

        binding.etNamaLengkap.setText(savedNama)
        binding.etTanggalLahir.setText(savedTglLahir)
        binding.etAlamat.setText(savedAlamat)
        binding.spinnerAgama.setSelection(savedAgamaPos)

        if (savedJenisKelamin == "Laki-laki") {
            binding.rbLakiLaki.isChecked = true
        } else if (savedJenisKelamin == "Perempuan") {
            binding.rbPerempuan.isChecked = true
        }
    }
}