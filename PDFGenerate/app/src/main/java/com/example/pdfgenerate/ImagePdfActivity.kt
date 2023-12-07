package com.example.pdfgenerate

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfgenerate.databinding.ActivityImagePdfBinding
import java.io.File
import java.io.FileOutputStream


class ImagePdfActivity : AppCompatActivity() {
    private lateinit var binding : ActivityImagePdfBinding
    private var bmp : Bitmap? = null
    private var scaledBmp : Bitmap? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Image add bitmap
        bmp = BitmapFactory.decodeResource(resources, R.drawable.image)
        scaledBmp = Bitmap.createScaledBitmap(bmp!!, 100, 100, false)
        permissionCheck()
        //Permission of Write storage
        //ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PackageManager.PERMISSION_GRANTED)

        createPDF()
    }

    private var storagePermissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private var storagePermission33 = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO,
        Manifest.permission.READ_MEDIA_VIDEO
    )

    private fun permissionCheck() : Array<String> {
        val p = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            storagePermission33
        } else {
            storagePermissions
        }
        return p
    }

    private fun createPDF() {
        binding.btnImage.setOnClickListener {
            val pdfDoc = PdfDocument()
            val myPaint = Paint()

            try {
                //Start PDF Generate
                val myPageInfo = PdfDocument.PageInfo.Builder(250, 400, 1).create()
                val myPage = pdfDoc.startPage(myPageInfo)
                val canvas = myPage.canvas
                canvas.drawBitmap(scaledBmp!!, 40f, 50f, myPaint)
                //PDF finish print inside create
                pdfDoc.finishPage(myPage)

                //PDF File Path
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "/imagePDF2.pdf")

                //Insert Of PDF Text
                pdfDoc.writeTo(FileOutputStream(file))
                //Close PDF File
                pdfDoc.close()
            } catch(e : Exception) {
                pdfDoc.close()
                e.printStackTrace()
            } finally {
                pdfDoc.close()
            }
            Toast.makeText(this, "Success!!", Toast.LENGTH_SHORT).show()
        }
    }
}