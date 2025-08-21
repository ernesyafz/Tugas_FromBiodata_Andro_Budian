package com.example.apkbiodata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apkbiodata.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        val nama = intent.getStringExtra("EXTRA_NAMA")
        val tanggalLahir = intent.getStringExtra("EXTRA_TANGGAL_LAHIR")
        val jenisKelamin = intent.getStringExtra("EXTRA_JENIS_KELAMIN")
        val agama = intent.getStringExtra("EXTRA_AGAMA")
        val alamat = intent.getStringExtra("EXTRA_ALAMAT")

        // Tampilkan data ke TextViews
        binding.tvHasilNama.text = "Nama Lengkap: $nama"
        binding.tvHasilTanggalLahir.text = "Tanggal Lahir: $tanggalLahir"
        binding.tvHasilJenisKelamin.text = "Jenis Kelamin: $jenisKelamin"
        binding.tvHasilAgama.text = "Agama: $agama"
        binding.tvHasilAlamat.text = "Alamat: $alamat"
    }
}