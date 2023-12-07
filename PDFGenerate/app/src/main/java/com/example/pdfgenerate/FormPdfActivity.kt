package com.example.pdfgenerate

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.pdfgenerate.databinding.ActivityFormPdfBinding
import java.io.File
import java.io.FileOutputStream

class FormPdfActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFormPdfBinding
    private val arr = arrayOf("Name", "Company Name", "Address", "Phone", "Email")

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PackageManager.PERMISSION_GRANTED)

        createPDF()
    }

    private fun createPDF() {
        binding.btnForm.setOnClickListener {
            val pdfDoc = PdfDocument()
            val myPaint = Paint()

            //regionTo PDF Page 1
            //Create Page 1
            val myPageInfo = PdfDocument.PageInfo.Builder(250, 400, 1).create()
            val myPage = pdfDoc.startPage(myPageInfo)
            val canvas = myPage.canvas

            //PDF Format align
            myPaint.textAlign = Paint.Align.CENTER
            myPaint.textSize = 12f

            //PDF Title
            canvas.drawText("JJM MARKET", myPageInfo.pageWidth / 2f, 30f, myPaint)
            //Sub Title PDF
            myPaint.textSize = 6f
            myPaint.color = Color.rgb(122, 119, 119)
            canvas.drawText("Sangam - 2, Godhra, Panchamahals, 389001", myPageInfo.pageWidth / 2f, 40f, myPaint)

            //Form Title Left
            myPaint.textAlign = Paint.Align.LEFT
            myPaint.textSize = 9f
            myPaint.color = Color.rgb(122, 119, 119)
            canvas.drawText("Customer Information", 10f, 70f, myPaint)

            //Form Data Left
            myPaint.textAlign = Paint.Align.LEFT
            myPaint.textSize = 8f
            myPaint.color = Color.BLACK
            //Form Data insert Position
            val startXPosition = 10f
            val endXPosition = myPageInfo.pageWidth - 10f
            var startYPosition = 100f
            //Form Data insert TO Table format
            for(i in 0 .. 4) {
                canvas.drawText(arr[i], startXPosition, startYPosition, myPaint)
                canvas.drawLine(startXPosition, startYPosition + 3f, endXPosition, startYPosition + 3f, myPaint)
                startYPosition += 20f
            }

            //PDF Draw Box
            canvas.drawLine(80f, 92f, 80f, 190f, myPaint)

            myPaint.style = Paint.Style.STROKE
            myPaint.strokeWidth = 2f
            canvas.drawRect(10f, 200f, myPageInfo.pageWidth - 10f, 300f, myPaint)
            canvas.drawLine(85f, 200f, 85f, 300f, myPaint)
            canvas.drawLine(163f, 200f, 163f, 300f, myPaint)

            //PDF BOX Inside Text
            myPaint.strokeWidth = 0f
            myPaint.style = Paint.Style.FILL
            canvas.drawText("Photo1", 35f, 250f, myPaint)
            canvas.drawText("Photo2", 110f, 250f, myPaint)
            canvas.drawText("Photo3", 190f, 250f, myPaint)

            //Normal Last Footer Text and Line Draw
            canvas.drawText("Photo3", 10f, 320f, myPaint)
            canvas.drawLine(35f, 325f, myPageInfo.pageWidth - 10f, 325f, myPaint)
            canvas.drawLine(10f, 345f, myPageInfo.pageWidth - 10f, 345f, myPaint)
            canvas.drawLine(10f, 365f, myPageInfo.pageWidth - 10f, 365f, myPaint)
            pdfDoc.finishPage(myPage)
            //endregion

            //PDF File Path
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "/formPDF.pdf")

            try {
                //Insert Of PDF Text
                pdfDoc.writeTo(FileOutputStream(file))
            } catch(e : Exception) {
                e.printStackTrace()
            }
            //Close PDF File
            pdfDoc.close()
            Toast.makeText(this, "Success!!", Toast.LENGTH_SHORT).show()
        }
    }
}