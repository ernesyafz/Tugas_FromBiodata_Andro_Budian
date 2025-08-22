package com.example.rakha_27

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvHouse: TextView
    private lateinit var tvBirthDate: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvReligion: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tvName = findViewById(R.id.tvName)
        tvHouse = findViewById(R.id.tvHouse)
        tvBirthDate = findViewById(R.id.tvBirthDate)
        tvGender = findViewById(R.id.tvGender)
        tvReligion = findViewById(R.id.tvReligion)
        btnBack = findViewById(R.id.btnBack)

        // Read extras
        val fullName = intent.getStringExtra("fullName") ?: ""
        val house = intent.getStringExtra("house") ?: ""
        val birthDate = intent.getStringExtra("birthDate") ?: ""
        val gender = intent.getStringExtra("gender") ?: ""
        val religion = intent.getStringExtra("religion") ?: ""

        tvName.text = fullName
        tvHouse.text = house
        tvBirthDate.text = birthDate
        tvGender.text = gender
        tvReligion.text = religion

        btnBack.setOnClickListener {
            finish()
        }
    }
}