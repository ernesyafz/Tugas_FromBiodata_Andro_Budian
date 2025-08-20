package com.example.formbiodata

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var tvHasil: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvHasil = findViewById(R.id.tvHasil)

        val nama = intent.getStringExtra("nama")
        val alamat = intent.getStringExtra("alamat")
        val tanggal = intent.getStringExtra("tanggal")
        val gender = intent.getStringExtra("gender")
        val agama = intent.getStringExtra("agama")

        tvHasil.text = """
            Nama: $nama
            Alamat: $alamat
            Tanggal Lahir: $tanggal
            Jenis Kelamin: $gender
            Agama: $agama
        """.trimIndent()
    }
}
