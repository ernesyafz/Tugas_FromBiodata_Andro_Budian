package com.example.biodata

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var btnChooseImage: Button
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNama = findViewById<EditText>(R.id.etNama)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)
        val etTanggal = findViewById<EditText>(R.id.etTanggal)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val spAgama = findViewById<Spinner>(R.id.spAgama)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        imgProfile = findViewById(R.id.imgProfile)
        btnChooseImage = findViewById(R.id.btnChooseImage)

        // Isi data agama di Spinner
        val agamaList = arrayOf("Islam", "Kristen", "Hindu", "Budha", "Katolik", "Konghucu")
        spAgama.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, agamaList)

        // Tanggal lahir pakai DatePickerDialog
        etTanggal.setOnClickListener {
            val c = Calendar.getInstance()
            val dpd = DatePickerDialog(this, { _, y, m, d ->
                etTanggal.setText("$d/${m + 1}/$y")
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            dpd.show()
        }

        // Pilih foto dari galeri
        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
                imgProfile.setImageURI(uri)
            }
        }

        btnChooseImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Load data dari SharedPreferences
        val sharedPref = getSharedPreferences("BiodataPref", Context.MODE_PRIVATE)
        etNama.setText(sharedPref.getString("NAMA", ""))
        etAlamat.setText(sharedPref.getString("ALAMAT", ""))
        etTanggal.setText(sharedPref.getString("TANGGAL", ""))

        val savedGender = sharedPref.getString("GENDER", "")
        if (savedGender == "Laki-laki") rgGender.check(R.id.rbLaki)
        if (savedGender == "Perempuan") rgGender.check(R.id.rbPerempuan)

        val savedAgama = sharedPref.getString("AGAMA", "")
        if (!savedAgama.isNullOrEmpty()) {
            val pos = agamaList.indexOf(savedAgama)
            if (pos >= 0) spAgama.setSelection(pos)
        }

        val savedImage = sharedPref.getString("FOTO", null)
        if (savedImage != null) {
            try {
                selectedImageUri = Uri.parse(savedImage)
                imgProfile.setImageURI(selectedImageUri)
            } catch (e: Exception) {
                imgProfile.setImageResource(R.mipmap.ic_launcher) // fallback
            }
        }

        // Tombol simpan
        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val tanggal = etTanggal.text.toString().trim()

            // Cek pilihan gender
            val selectedGenderId = rgGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else ""

            // Cek agama
            val agama = if (spAgama.selectedItem != null) {
                spAgama.selectedItem.toString()
            } else ""

            // Validasi input
            if (nama.isEmpty() || alamat.isEmpty() || tanggal.isEmpty() || gender.isEmpty() || agama.isEmpty()) {
                Toast.makeText(this, "Semua form harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan ke SharedPreferences
            with(sharedPref.edit()) {
                putString("NAMA", nama)
                putString("ALAMAT", alamat)
                putString("TANGGAL", tanggal)
                putString("GENDER", gender)
                putString("AGAMA", agama)
                putString("FOTO", selectedImageUri?.toString())
                apply()
            }

            // Kirim data ke ResultActivity
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("NAMA", nama)
                putExtra("ALAMAT", alamat)
                putExtra("TANGGAL", tanggal)
                putExtra("GENDER", gender)
                putExtra("AGAMA", agama)
                putExtra("FOTO", selectedImageUri?.toString())
            }
            startActivity(intent)
        }
    }
}
