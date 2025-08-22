package com.example.projectandroidaplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvHasil: TextView = findViewById(R.id.tvHasil)

        val nama = intent.getStringExtra("NAMA")
        val alamat = intent.getStringExtra("ALAMAT")
        val tgl = intent.getStringExtra("TGL")
        val gender = intent.getStringExtra("GENDER")
        val agama = intent.getStringExtra("AGAMA")

        val hasil = """
            Biodata:
            
            Nama         : $nama
            Alamat       : $alamat
            Tgl Lahir    : $tgl
            Jenis Kelamin: $gender
            Agama        : $agama
        """.trimIndent()

        tvHasil.text = hasil
    }
}