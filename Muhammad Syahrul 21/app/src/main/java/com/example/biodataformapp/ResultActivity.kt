package com.example.biodataformapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvNama = findViewById<TextView>(R.id.tvNama)
        val tvAlamat = findViewById<TextView>(R.id.tvAlamat)
        val tvTanggal = findViewById<TextView>(R.id.tvTanggal)
        val tvGender = findViewById<TextView>(R.id.tvGender)
        val tvAgama = findViewById<TextView>(R.id.tvAgama)

        // 🔑 Ambil data dari intent
        val nama = intent.getStringExtra("nama")      // harus persis "nama"
        val alamat = intent.getStringExtra("alamat")
        val tanggal = intent.getStringExtra("tanggal")
        val gender = intent.getStringExtra("gender")
        val agama = intent.getStringExtra("agama")

        // Tampilkan
        tvNama.text = "Nama: $nama"
        tvAlamat.text = "Alamat: $alamat"
        tvTanggal.text = "Tanggal Lahir: $tanggal"
        tvGender.text = "Jenis Kelamin: $gender"
        tvAgama.text = "Agama: $agama"
    }
}
