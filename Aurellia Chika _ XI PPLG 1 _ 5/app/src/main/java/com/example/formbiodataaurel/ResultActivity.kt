package com.example.formbiodataaurel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    private lateinit var tvNama: TextView
    private lateinit var tvAlamat: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvTglLahir: TextView
    private lateinit var tvAgama: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvNama = findViewById(R.id.tvNama)
        tvAlamat = findViewById(R.id.tvAlamat)
        tvGender = findViewById(R.id.tvGender)
        tvTglLahir = findViewById(R.id.tvTglLahir)
        tvAgama = findViewById(R.id.tvAgama)

        val nama = intent.getStringExtra("nama")
        val alamat = intent.getStringExtra("alamat")
        val gender = intent.getStringExtra("gender")
        val tglLahir = intent.getStringExtra("tglLahir")
        val agama = intent.getStringExtra("agama")

        tvNama.text = "Nama: $nama"
        tvAlamat.text = "Alamat: $alamat"
        tvGender.text = "Jenis Kelamin: $gender"
        tvTglLahir.text = "Tanggal Lahir: $tglLahir"
        tvAgama.text = "Agama: $agama"
    }
}
