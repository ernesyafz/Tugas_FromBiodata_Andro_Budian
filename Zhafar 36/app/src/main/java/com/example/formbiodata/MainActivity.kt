package com.example.formbiodata

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nama = findViewById<EditText>(R.id.nama)
        val alamat = findViewById<EditText>(R.id.alamat)
        val simpan = findViewById<Button>(R.id.simpan)
        val hasil = findViewById<TextView>(R.id.hasil)

        simpan.setOnClickListener {
            val namaInput = nama.text.toString()
            val alamatInput = alamat.text.toString()
            hasil.text = "Nama: $namaInput\nAlamat: $alamatInput"
        }
    }
}
