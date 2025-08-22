package com.example.biodata

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var imgResultProfile: ImageView
    private lateinit var txtNama: TextView
    private lateinit var txtAlamat: TextView
    private lateinit var txtTanggal: TextView
    private lateinit var txtGender: TextView
    private lateinit var txtAgama: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        imgResultProfile = findViewById(R.id.imgResultProfile)
        txtNama = findViewById(R.id.txtNama)
        txtAlamat = findViewById(R.id.txtAlamat)
        txtTanggal = findViewById(R.id.txtTanggal)
        txtGender = findViewById(R.id.txtGender)
        txtAgama = findViewById(R.id.txtAgama)

        val sharedPref = getSharedPreferences("Biodata", MODE_PRIVATE)
        txtNama.text = sharedPref.getString("nama", "")
        txtAlamat.text = sharedPref.getString("alamat", "")
        txtTanggal.text = sharedPref.getString("tanggal", "")
        txtGender.text = sharedPref.getString("gender", "")
        txtAgama.text = sharedPref.getString("agama", "")

        val foto = sharedPref.getString("foto", null)
        foto?.let {
            try {
                val uri = Uri.parse(it)
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imgResultProfile.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
