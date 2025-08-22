package com.example.biodatatitan

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Ambil data dari Intent
        val nama = intent.getStringExtra("EXTRA_NAMA")
        val alamat = intent.getStringExtra("EXTRA_ALAMAT")
        val tanggalLahir = intent.getStringExtra("EXTRA_TANGGAL_LAHIR")
        val jenisKelamin = intent.getStringExtra("EXTRA_JENIS_KELAMIN")
        val agama = intent.getStringExtra("EXTRA_AGAMA")

        // Inisialisasi TextView
        val textViewResultNama: TextView = findViewById(R.id.textViewResultNama)
        val textViewResultAlamat: TextView = findViewById(R.id.textViewResultAlamat)
        val textViewResultTanggalLahir: TextView = findViewById(R.id.textViewResultTanggalLahir)
        val textViewResultJenisKelamin: TextView = findViewById(R.id.textViewResultJenisKelamin)
        val textViewResultAgama: TextView = findViewById(R.id.textViewResultAgama)

        // Set teks ke TextView menggunakan String Template
        textViewResultNama.text = "Nama: $nama"
        textViewResultAlamat.text = "Alamat: $alamat"
        textViewResultTanggalLahir.text = "Tanggal Lahir: $tanggalLahir"
        textViewResultJenisKelamin.text = "Jenis Kelamin: $jenisKelamin"
        textViewResultAgama.text = "Agama: $agama"
    }
}