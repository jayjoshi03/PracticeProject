package com.example.pdfgenerate

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import com.example.pdfgenerate.databinding.ActivityMultiPageBinding
import java.io.File
import java.io.FileOutputStream

class MultiPageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMultiPageBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PackageManager.PERMISSION_GRANTED)

        createPDF()
    }

    private fun createPDF() {
        binding.btnMulti.setOnClickListener {
            val pdfDoc=PdfDocument()
            val myPaint=Paint()

            //Create Page 1
            val myPageInfo=PdfDocument.PageInfo.Builder(250, 400, 1).create()
            val myPage1=pdfDoc.startPage(myPageInfo)
            val canvas=myPage1.canvas

            //PDF Page 1 Text
            canvas.drawText("Introduction Page 1", 40f, 50f, myPaint)
            pdfDoc.finishPage(myPage1)

            //Create Page 2
            val myPageInfo2=PdfDocument.PageInfo.Builder(250, 400, 1).create()
            val myPage2=pdfDoc.startPage(myPageInfo2)
            val canvas2=myPage2.canvas

            //PDF Page 2 Text
            canvas2.drawText("Introduction Page 2", 40f, 50f, myPaint)
            pdfDoc.finishPage(myPage2)

            //PDF File Path
            val file=File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "/multiPagePDF.pdf")

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