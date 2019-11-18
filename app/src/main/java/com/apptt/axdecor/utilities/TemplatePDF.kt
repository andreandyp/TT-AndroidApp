package com.apptt.axdecor.Utilities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.apptt.axdecor.R
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TemplatePDF(private val context: Context) {
    lateinit var pdfFile: File
    lateinit var document: Document
    lateinit var pdfWriter: PdfWriter
    lateinit var paragraph: Paragraph
    var fTitle = Font(Font.FontFamily.TIMES_ROMAN, 20f, Font.BOLD)
    var fSubTitle = Font(Font.FontFamily.TIMES_ROMAN, 20f, Font.BOLD)
    var fText = Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD)
    var fHighText = Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.BOLD, BaseColor.RED)

    fun openDocument() {
        createFile()
        try {
            document = Document(PageSize.A4)
            pdfWriter = PdfWriter.getInstance(document, FileOutputStream(pdfFile))
            document.open()
        } catch (e: Exception) {
            Log.e("openDocument", e.toString())
        }
    }

    fun createFile() {
        val date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val folder =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + File.separator + "AXDecorListas")
        if (!folder.exists())
            folder.mkdirs()
        pdfFile = File(folder, "ListaAXdecor$date.pdf")
    }

    fun closeDocument() {
        document.close()
    }

    fun addMetaData(title: String, subject: String, author: String) {
        document.addTitle(title)
        document.addSubject(subject)
        document.addAuthor(author)
    }

    fun addTitles(title: String, subTitle: String, date: String) {
        paragraph = Paragraph()
        addChildP(Paragraph(title, fTitle))
        addChildP(Paragraph(subTitle, fSubTitle))
        addChildP(Paragraph("Generado: $date", fHighText))
        paragraph.spacingAfter = 30f
        document.add(paragraph)
    }

    private fun addChildP(childParagraph: Paragraph) {
        childParagraph.alignment = Element.ALIGN_CENTER
        paragraph.add(childParagraph)
    }

    fun addParagraph(text: String) {
        paragraph = Paragraph(text, fText)
        paragraph.spacingAfter = 5f
        paragraph.spacingBefore = 5f
        document.add(paragraph)
    }

    fun createTable(header: Array<String>, clients: ArrayList<Array<String>>, total: Float) {
        paragraph = Paragraph()
        paragraph.font = fText
        var pdfTable = PdfPTable(header.size) //Numero de columnas
        pdfTable.widthPercentage = 100f
        pdfTable.spacingBefore = 20f
        var pdfPCell: PdfPCell
        var indexC = 0
        while (indexC < header.size) {
            pdfPCell = PdfPCell(Phrase(header[indexC++], fSubTitle))
            pdfPCell.horizontalAlignment = Element.ALIGN_CENTER
            pdfPCell.backgroundColor = BaseColor(74, 189, 172)
            pdfTable.addCell(pdfPCell)
        }
        clients.forEachIndexed { index, it ->
            var row = it
            header.forEachIndexed { index2, s ->
                pdfPCell = PdfPCell(Phrase(row[index2]))
                pdfPCell.horizontalAlignment = Element.ALIGN_CENTER
                pdfPCell.fixedHeight = 48f
                pdfTable.addCell(pdfPCell)
            }
        }
        header.forEachIndexed { index, s ->
            if (index == header.size - 1) {
                pdfPCell = PdfPCell(Phrase("Total: $total"))
            } else {
                pdfPCell = PdfPCell(Phrase(""))
            }
            pdfPCell.horizontalAlignment = Element.ALIGN_CENTER
            pdfPCell.backgroundColor = BaseColor(74, 189, 172)
            pdfTable.addCell(pdfPCell)
        }
        paragraph.add(pdfTable)
        document.add(paragraph)
    }

    fun verPDF(activity: Activity) {
        if (pdfFile.exists()) {
            var mIntent = Intent(Intent.ACTION_VIEW)
            mIntent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf")
            try {
                activity.startActivity(mIntent)
            } catch (e: ActivityNotFoundException) {
                //Descarga Adobe Reader si no tienes con que abrir PDF's
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.adobe.reader")
                    )
                )
                Toast.makeText(context, "No tienes app para ver PDFS", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "No se encontro archivo", Toast.LENGTH_SHORT).show()
        }
    }
}