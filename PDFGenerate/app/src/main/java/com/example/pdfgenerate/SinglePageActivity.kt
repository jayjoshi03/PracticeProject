package com.example.pdfgenerate

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.pdfgenerate.databinding.ActivitySinglePageBinding
import java.io.File
import java.io.FileOutputStream

class SinglePageActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySinglePageBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySinglePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PackageManager.PERMISSION_GRANTED)

        createPDF()
    }

    private fun createPDF() {
        binding.btnPdf.setOnClickListener {
            val pdfDoc=PdfDocument()
            val myPaint=Paint()

            //Create Page 1
            val myPageInfo=PdfDocument.PageInfo.Builder(250, 400, 1).create()
            val myPage1=pdfDoc.startPage(myPageInfo)
            val canvas=myPage1.canvas

            //PDF Page 1 Text
            canvas.drawText("Hi How are You?", 40f, 50f, myPaint)
            pdfDoc.finishPage(myPage1)

            //PDF File Path
            val file=File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "/singlePagePDF.pdf")

            try {
                //Insert Of PDF Text
                pdfDoc.writeTo(FileOutputStream(file))
            } catch (e : Exception) {
                e.printStackTrace()
            }
            //Close PDF File
            pdfDoc.close()
        }
    }
}