package com.example.generateapd

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.generateapd.databinding.ActivityMainBinding
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File

class MainActivity : AppCompatActivity() {
    companion object {
        private const val WRITE_EXTERNAL_STORAGE_PERMISSION=1
    }

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pdfGenerate.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                generatePDF()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_PERMISSION)
            }
        }

    }

    private fun generatePDF() {
        val pdfPath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val pdfFile=File(pdfPath, "pdfItem.pdf")

        //Create a new PDF
        val pdfWrite=PdfWriter(pdfFile)
        val pdfDocument=PdfDocument(pdfWrite)
        val document=Document(pdfDocument)

        //Text
        val text="Hello!"
        val para=Paragraph(text)
        document.add(para)

        //Close
        document.close()

        Toast.makeText(this, "Success!!", Toast.LENGTH_SHORT).show()
    }
}