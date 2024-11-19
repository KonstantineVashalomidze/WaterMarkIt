package com.markit

import com.markit.api.WatermarkingMethod
import com.markit.api.WatermarkPosition
import com.markit.api.WatermarkService
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.awt.Color
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.concurrent.Executors
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PdfPortraitPageOrientationTextBasedWatermarkTest {
    private lateinit var document: PDDocument

    @BeforeEach
    fun initDocument() {
        document = PDDocument().apply {
            addPage(PDPage())
            addPage(PDPage())
            addPage(PDPage())
        }
    }

    @AfterEach
    fun close(){
        document.close()
    }

    @Test
    @Throws(IOException::class)
    fun `given Portrait Pdf when Draw Method is Used then Make Watermarked Pdf`() {
        // When
        val result = WatermarkService.textBasedWatermarker(
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors()
                )
        )
            .watermark(document)
                    .withText("Top Left Watermark")
                    .size(50)
                    .position(WatermarkPosition.TOP_LEFT)
                    .method(WatermarkingMethod.DRAW)
                    .color(Color.BLACK)
                    .dpi(300f)
                .and()
                    .withText("Center Watermark")
                    .size(150)
                    .method(WatermarkingMethod.DRAW)
                    .addTrademark()
                    .rotation(45)
                    .position(WatermarkPosition.CENTER)
                    .color(Color.BLUE)
                .apply()

        // Then
        assertNotNull(result, "The resulting byte array should not be null")
        assertTrue(result.isNotEmpty(), "The resulting byte array should not be empty")
        //outputFile(result, "DrawPlainPdf.pdf")
    }

    @Test
    @Throws(IOException::class)
    fun `given Portrait Pdf when Overlay Method then Make Watermarked Pdf`() {
        // When
        val result = WatermarkService.textBasedWatermarker()
                .watermark(document)
                .withText("Sample Watermark")
                .size(40)
                .method(WatermarkingMethod.OVERLAY) // Overlay mode isn't resource-consuming, so a thread pool isn't necessary.
                .position(WatermarkPosition.CENTER)
                .rotation(45)
                .addTrademark()
                .opacity(0.2f)
                .color(Color.RED)
                .apply()

        // Then
        assertNotNull(result, "The resulting byte array should not be null")
        assertTrue(result.isNotEmpty(), "The resulting byte array should not be empty")
        //outputFile(result, "OverlayPlainPdf.pdf")
    }

    @Test
    @Throws(IOException::class)
    fun `given Portrait Pdf when Draw Method and TILED position is Used then Make Watermarked Pdf`() {
        // When
        val result = WatermarkService.textBasedWatermarker(
            Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
            )
        )
            .watermark(document)
            .withText("CISCO").size(194)
            .method(WatermarkingMethod.DRAW)
            .position(WatermarkPosition.TILED)
            .color(Color.RED)
            .opacity(0.5f)
            .dpi(300f)
            .apply()

        // Then
        assertNotNull(result, "The resulting byte array should not be null")
        assertTrue(result.isNotEmpty(), "The resulting byte array should not be empty")
        //outputFile(result, "tiled.pdf")
    }

    private fun outputFile(result: ByteArray, filename: String) {
        val outputFile = File(filename)
        Files.write(outputFile.toPath(), result)
    }
}