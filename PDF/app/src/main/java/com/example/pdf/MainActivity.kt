package com.example.pdf

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pdf.databinding.ActivityMainBinding
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        private val FILE_PRINT : String="PDFPrint.pdf"

        fun getBitmapFromUrl(context : Context, model : PDFModel, document : Document) : io.reactivex.Observable<PDFModel> {
            return io.reactivex.Observable.fromCallable {
                val bitmap=Glide.with(context)
                    .asBitmap()
                    .load(model.image)
                    .submit().get()
                val image=Image.getInstance(bitmapToByteArray(bitmap))
                image.scaleAbsolute(100f, 100f)
                document.add(image)
                model
            }
        }

        private fun bitmapToByteArray(bitmap : Bitmap?) : ByteArray {
            val stream=ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }
    }

    lateinit var binding : ActivityMainBinding
    var pdfModel=ArrayList<PDFModel>()

    private val appPath : String
        get() {
            val dir=File(Environment.getExternalStorageDirectory().toString() + File.separator + resources.getString(R.string.app_name) + File.separator)
            if (!dir.exists()) dir.mkdir()
            return dir.path + File.separator
        }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initModel()

        Dexter.withActivity(this)
            .withPermission(Manifest.permission_group.STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response : PermissionGrantedResponse?) {
                    binding.btnGeneratePdf.setOnClickListener {
                        createPDFFile(StringBuilder(appPath).append(FILE_PRINT).toString())
                    }
                }

                override fun onPermissionDenied(response : PermissionDeniedResponse?) {
                    Toast.makeText(this@MainActivity, "Please enable this Permission!", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(permission : PermissionRequest?, token : PermissionToken?) {
                    TODO("Not yet implemented")
                }

            })
            .check() //Don't forget it ^^
    }

    private fun createPDFFile(path : String) {
        if (File(path).exists()) File(path).delete()
        try {
            val document=Document()
            //Save Document
            PdfWriter.getInstance(document, FileOutputStream(path))
            //Open Document
            document.open()

            //Setting of Document
            document.pageSize=PageSize.A4
            document.addCreationDate()
            document.addAuthor("Jay")
            document.addCreator("JJ")

            //Font Setting
//            val colorAccent=BaseColor(0, 153, 204, 255)
//            val fontSize=20.0f

            //Custom Font
            val fontName=BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED)

            //Create title of Document
            val titleFont=Font(fontName, 36.0f, Font.NORMAL, BaseColor.BLACK)
            PDFUtils.addNewItem(document, "Super Heroes", Element.ALIGN_CENTER, titleFont)

            //Add More
            PDFUtils.addLineSpace(document)
            PDFUtils.addNewItem(document, "Detail", Element.ALIGN_CENTER, titleFont)
            PDFUtils.addLineSeparator(document)

            //Use RxJava tp fetch image and add to PDF
            io.reactivex.Observable.fromIterable(pdfModel)
                .flatMap { model : PDFModel-> getBitmapFromUrl(this, model, document) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ model : PDFModel->
                    //On next
                    PDFUtils.addNewItemWithLeftAndRight(document, model.name, "", titleFont, titleFont)
                    PDFUtils.addLineSeparator(document)
                    PDFUtils.addNewItem(document, model.description, Element.ALIGN_LEFT, titleFont)
                    PDFUtils.addLineSeparator(document)
                }, { t : Throwable?->
                    //On Error
                    Toast.makeText(this, t!!.message, Toast.LENGTH_SHORT).show()
                }, {
                    //On Complete
                    PDFUtils.addLineSpace(document)
                    PDFUtils.addLineSpace(document)

                    //Close
                    document.close()
                    Toast.makeText(this, "Success!!", Toast.LENGTH_SHORT).show()
                    printPDF()
                })
        } catch (e : FileNotFoundException) {
            e.printStackTrace()
        } catch (ei : IOException) {
            ei.printStackTrace()
        } catch (ed : DocumentException) {
            ed.printStackTrace()
        } finally {

        }
    }

    private fun printPDF() {
        val printManager=getSystemService(Context.PRINT_SERVICE) as PrintManager
        try {
            val printDocumentAdapter=PDFDocumentAdapter(StringBuilder(appPath).append(FILE_PRINT).toString(), FILE_PRINT)
            printManager.print("Document", printDocumentAdapter, PrintAttributes.Builder().build())
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    private fun initModel() {
        val model=PDFModel()
        model.name="KGF 2"
        model.image=""
        model.description="This is best Movie List"

        pdfModel.add(model)
    }
}