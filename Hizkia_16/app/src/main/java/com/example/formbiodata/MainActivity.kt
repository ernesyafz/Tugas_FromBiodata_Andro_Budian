package com.example.biodata

import android.net.Uri
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var edtNama: EditText
    private lateinit var edtAlamat: EditText
    private lateinit var edtTanggalLahir: EditText
    private lateinit var radioGender: RadioGroup
    private lateinit var spinnerAgama: Spinner
    private lateinit var btnSimpan: Button
    private lateinit var imgProfile: ImageView

    private lateinit var sharedPref: SharedPreferences
    private var selectedImageUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            val inputStream: InputStream? = contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imgProfile.setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtNama = findViewById(R.id.edtNama)
        edtAlamat = findViewById(R.id.edtAlamat)
        edtTanggalLahir = findViewById(R.id.edtTanggalLahir)
        radioGender = findViewById(R.id.radioGender)
        spinnerAgama = findViewById(R.id.spinnerAgama)
        btnSimpan = findViewById(R.id.btnSimpan)
        imgProfile = findViewById(R.id.imgProfile)

        sharedPref = getSharedPreferences("Biodata", MODE_PRIVATE)

        // Isi Spinner
        val agamaList = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Konghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, agamaList)
        spinnerAgama.adapter = adapter

        // DatePicker
        edtTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dp = DatePickerDialog(this, { _, y, m, d ->
                edtTanggalLahir.setText("$d/${m + 1}/$y")
            }, year, month, day)
            dp.show()
        }

        // Upload Foto
        imgProfile.setOnClickListener {
            pickImage.launch("image/*")
        }

        // Simpan dan Pindah ke ResultActivity
        btnSimpan.setOnClickListener {
            val nama = edtNama.text.toString()
            val alamat = edtAlamat.text.toString()
            val tanggal = edtTanggalLahir.text.toString()
            val genderId = radioGender.checkedRadioButtonId
            val agama = spinnerAgama.selectedItem.toString()

            if (nama.isEmpty() || alamat.isEmpty() || tanggal.isEmpty() || genderId == -1) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val gender = findViewById<RadioButton>(genderId).text.toString()

                // Simpan ke SharedPreferences
                val editor = sharedPref.edit()
                editor.putString("nama", nama)
                editor.putString("alamat", alamat)
                editor.putString("tanggal", tanggal)
                editor.putString("gender", gender)
                editor.putString("agama", agama)
                editor.putString("foto", selectedImageUri?.toString())
                editor.apply()

                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
