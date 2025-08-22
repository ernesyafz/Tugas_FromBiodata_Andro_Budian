package com.example.biodata

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etTanggal: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var spinnerAgama: Spinner
    private lateinit var imgProfile: ImageView
    private lateinit var btnPilihFoto: Button
    private lateinit var btnKirim: Button

    private var fotoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi View
        etNama = findViewById(R.id.etNama)
        etAlamat = findViewById(R.id.etAlamat)
        etTanggal = findViewById(R.id.etTanggal)
        spinnerGender = findViewById(R.id.spinnerGender)
        spinnerAgama = findViewById(R.id.spinnerAgama)
        imgProfile = findViewById(R.id.imgProfile)
        btnPilihFoto = findViewById(R.id.btnPilihFoto)
        btnKirim = findViewById(R.id.btnKirim)

        // Spinner Gender
        val genderList = listOf("Pilih Gender", "Laki-laki", "Perempuan")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = genderAdapter

        // Spinner Agama
        val agamaList = listOf("Pilih Agama", "Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
        val agamaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, agamaList)
        agamaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAgama.adapter = agamaAdapter

        // Tombol pilih foto
        btnPilihFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        // Klik tanggal lahir → DatePicker
        etTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                etTanggal.setText("$d/${m + 1}/$y")
            }, year, month, day).show()
        }

        // Tombol kirim
        btnKirim.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val tanggal = etTanggal.text.toString().trim()
            val gender = spinnerGender.selectedItem.toString()
            val agama = spinnerAgama.selectedItem.toString()

            if (nama.isEmpty() || alamat.isEmpty() || tanggal.isEmpty() ||
                gender == "Pilih Gender" || agama == "Pilih Agama") {
                Toast.makeText(this, "Lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("NAMA", nama)
            intent.putExtra("ALAMAT", alamat)
            intent.putExtra("TANGGAL", tanggal)
            intent.putExtra("GENDER", gender)
            intent.putExtra("AGAMA", agama)
            if (fotoUri != null) intent.putExtra("FOTO", fotoUri.toString())
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            fotoUri = data?.data
            imgProfile.setImageURI(fotoUri)
        }
    }
}
