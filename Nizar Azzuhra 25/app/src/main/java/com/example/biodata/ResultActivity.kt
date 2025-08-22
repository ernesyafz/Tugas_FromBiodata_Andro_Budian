package com.example.biodata

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvResult = findViewById<TextView>(R.id.tvResult)
        val imgProfileResult = findViewById<ImageView>(R.id.imgProfileResult)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Ambil data dari intent (pakai default "-" kalau kosong)
        val nama = intent.getStringExtra("NAMA") ?: "-"
        val alamat = intent.getStringExtra("ALAMAT") ?: "-"
        val tanggal = intent.getStringExtra("TANGGAL") ?: "-"
        val gender = intent.getStringExtra("GENDER") ?: "-"
        val agama = intent.getStringExtra("AGAMA") ?: "-"
        val fotoUri = intent.getStringExtra("FOTO")

        // Tampilkan biodata ke TextView
        tvResult.text = """
            Nama      : $nama
            Alamat    : $alamat
            Tgl Lahir : $tanggal
            Gender    : $gender
            Agama     : $agama
        """.trimIndent()

        // Tampilkan foto profil jika ada
        if (!fotoUri.isNullOrEmpty()) {
            try {
                val uri = Uri.parse(fotoUri)
                imgProfileResult.setImageURI(uri)
            } catch (e: Exception) {
                Log.e("ResultActivity", "Gagal memuat foto: ${e.message}")
                imgProfileResult.setImageResource(R.mipmap.ic_launcher) // fallback
            }
        } else {
            imgProfileResult.setImageResource(R.mipmap.ic_launcher) // default
        }

        // Tombol kembali ke MainActivity
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
