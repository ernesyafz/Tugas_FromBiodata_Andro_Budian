package com.example.biodata

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var imgResult: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvResult = findViewById(R.id.tvResult)
        imgResult = findViewById(R.id.imgResult)

        val nama = intent.getStringExtra("NAMA")
        val alamat = intent.getStringExtra("ALAMAT")
        val tanggal = intent.getStringExtra("TANGGAL")
        val gender = intent.getStringExtra("GENDER")
        val agama = intent.getStringExtra("AGAMA")
        val foto = intent.getStringExtra("FOTO")

        tvResult.text = """
            Nama    : $nama
            Alamat  : $alamat
            Tanggal : $tanggal
            Gender  : $gender
            Agama   : $agama
        """.trimIndent()

        if (!foto.isNullOrEmpty()) {
            imgResult.setImageURI(Uri.parse(foto))
        }
    }
}
