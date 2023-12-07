package com.example.pdf

import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class PDFDocumentAdapter(private var path : String, private var fileName : String) : PrintDocumentAdapter() {
    override fun onLayout(printA : PrintAttributes?, printB : PrintAttributes?, cancelSignal : CancellationSignal?, layoutResult : LayoutResultCallback?, p4 : Bundle?) {
        if (cancelSignal!!.isCanceled) layoutResult!!.onLayoutCancelled()
        else {
            val builder=PrintDocumentInfo.Builder(fileName)
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                .build()
            layoutResult!!.onLayoutFinished(builder.build(), printB != printA)
        }
    }

    override fun onWrite(pageRange : Array<out PageRange>?, parcelFileDescriptor : ParcelFileDescriptor?, cancelSignal : CancellationSignal?, writeResult : WriteResultCallback?) {
        var inputIO : InputStream? = null
        var outputIO : OutputStream? = null

        try {
            val file = File(path)
            inputIO = FileInputStream(file)
            outputIO = FileOutputStream(parcelFileDescriptor!!.fileDescriptor)
            val buff = ByteArray(16384)
            var size:Int
            while ((inputIO.read(buff).also { size=it } >= 0) && !cancelSignal!!.isCanceled) outputIO.write(buff,0,size)
            if (cancelSignal!!.isCanceled)writeResult!!.onWriteCancelled() else writeResult!!.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
        }catch (e:FileNotFoundException){
            e.printStackTrace()
        }catch (ex:IOException){
            ex.printStackTrace()
        }finally {
            try {
                inputIO!!.close()
                outputIO!!.close()
            }catch (ex:IOException){
                ex.printStackTrace()
            }
        }
    }
}