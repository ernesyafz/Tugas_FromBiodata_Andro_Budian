package com.example.amad // Pastikan ini sesuai dengan package name proyek Anda

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Deklarasi variabel untuk komponen UI
    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var spinnerAgama: Spinner
    private lateinit var btnSimpan: Button

    // Objek Calendar untuk DatePickerDialog
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengatur layout untuk aktivitas ini
        setContentView(R.layout.activity_main)

        // Inisialisasi komponen UI dengan ID yang sesuai dari layout
        etNama = findViewById(R.id.etNama)
        etAlamat = findViewById(R.id.etAlamat)
        etTanggalLahir = findViewById(R.id.etTanggalLahir)
        rgJenisKelamin = findViewById(R.id.rgJenisKelamin)
        spinnerAgama = findViewById(R.id.spinnerAgama)
        btnSimpan = findViewById(R.id.btnSimpan)

        // Mengatur Spinner untuk pilihan agama
        setupAgamaSpinner()

        // Mengatur DatePickerDialog untuk input tanggal lahir
        setupDatePicker()

        // Menambahkan OnClickListener pada tombol "Tampilkan Hasil"
        btnSimpan.setOnClickListener {
            validateAndShowResult()
        }
    }

    // Fungsi untuk mengisi data ke Spinner pilihan agama
    private fun setupAgamaSpinner() {
        // Menambahkan "Pilih Agama" sebagai opsi pertama
        val agamaList = arrayOf("Pilih Agama", "Islam", "Kristen Protestan", "Kristen Katolik", "Hindu", "Budha", "Konghucu", "Lainnya")
        // Membuat ArrayAdapter untuk Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, agamaList)
        // Mengatur layout drop-down untuk Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Menetapkan adapter ke Spinner
        spinnerAgama.adapter = adapter
    }

    // Fungsi untuk menampilkan DatePickerDialog ketika EditText tanggal lahir diklik
    private fun setupDatePicker() {
        // Ketika etTanggalLahir diklik
        etTanggalLahir.setOnClickListener {
            showDatePickerDialog()
        }
    }

    // Fungsi untuk menampilkan DatePickerDialog
    private fun showDatePickerDialog() {
        // Membuat instance DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                // Ketika tanggal dipilih, update objek Calendar
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                // Format tanggal dan set ke EditText
                updateDateInView()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Tampilkan dialog
        datePickerDialog.show()
    }

    // Fungsi untuk memperbarui teks di EditText Tanggal Lahir
    private fun updateDateInView() {
        // Format tanggal menjadi "dd/MM/yyyy"
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        // Set tanggal yang diformat ke EditText
        etTanggalLahir.setText(sdf.format(calendar.time))
    }

    // Fungsi untuk memvalidasi input dan menampilkan hasilnya
    private fun validateAndShowResult() {
        // Mengambil teks dari EditText dan menghapus spasi di awal/akhir
        val nama = etNama.text.toString().trim()
        val alamat = etAlamat.text.toString().trim()
        val tanggalLahir = etTanggalLahir.text.toString().trim()

        // Mengambil ID RadioButton yang terpilih dari RadioGroup
        val jenisKelaminId = rgJenisKelamin.checkedRadioButtonId
        // Mengambil teks pilihan agama dari Spinner
        val agama = spinnerAgama.selectedItem.toString()

        // --- Validasi Data ---
        // Cek apakah semua field nama, alamat, dan tanggal lahir kosong
        if (nama.isEmpty()) {
            Toast.makeText(this, "Nama lengkap tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return // Hentikan eksekusi jika validasi gagal
        }
        if (alamat.isEmpty()) {
            Toast.makeText(this, "Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }
        if (tanggalLahir.isEmpty()) {
            Toast.makeText(this, "Tanggal lahir tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }
        // Cek apakah tidak ada RadioButton jenis kelamin yang terpilih
        if (jenisKelaminId == -1) {
            Toast.makeText(this, "Pilih jenis kelamin!", Toast.LENGTH_SHORT).show()
            return
        }
        // Cek apakah pilihan agama masih "Pilih Agama" (default)
        if (agama == "Pilih Agama") {
            Toast.makeText(this, "Pilih agama Anda!", Toast.LENGTH_SHORT).show()
            return
        }
        // --- Akhir Validasi Data ---

        // Mengambil teks dari RadioButton yang terpilih
        val jenisKelamin = when (jenisKelaminId) {
            R.id.rbLakiLaki -> "Laki-laki"
            R.id.rbPerempuan -> "Perempuan"
            else -> "Tidak Diketahui" // Seharusnya tidak tercapai karena sudah divalidasi
        }

        // Membuat string hasil biodata yang akan ditampilkan
        val resultText = """
            Nama Lengkap: $nama
            Alamat: $alamat
            Tanggal Lahir: $tanggalLahir
            Jenis Kelamin: $jenisKelamin
            Agama: $agama
        """.trimIndent() // trimIndent() menghapus indentasi kosong di awal baris

        // Menampilkan hasil biodata menggunakan AlertDialog
        AlertDialog.Builder(this)
            .setTitle("Hasil Biodata") // Judul dialog
            .setMessage(resultText) // Pesan dialog (hasil biodata)
            .setPositiveButton("OK") { dialog, _ ->
                // Aksi ketika tombol "OK" diklik (menutup dialog)
                dialog.dismiss()
            }
            .show() // Menampilkan dialog
    }
}
