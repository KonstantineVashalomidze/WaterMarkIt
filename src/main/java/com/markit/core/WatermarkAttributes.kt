package com.markit.core

import com.markit.core.positioning.WatermarkPosition
import com.markit.core.positioning.WatermarkPositionCoordinates
import org.apache.pdfbox.pdmodel.PDDocument
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*
import java.util.function.Predicate

/**
 * @author Oleg Cheban
 * @since 1.0
 */
data class WatermarkAttributes (
    var text: String = "",
    var image: Optional<BufferedImage> = Optional.empty(),
    var size: Int = 100,
    var color: Color = Color.BLACK,
    var opacity: Int = 40,
    var dpi: Float = 300f,
    var trademark: Boolean = false,
    var rotationDegrees: Int = 0,
    var method: WatermarkingMethod = WatermarkingMethod.DRAW,
    var position: WatermarkPosition = WatermarkPosition.CENTER,
    var positionAdjustment: WatermarkPositionCoordinates.Coordinates = WatermarkPositionCoordinates.Coordinates(0, 0),
    var verticalSpacing: Int = 50,
    var horizontalSpacing: Int = 50,
    var documentPredicate: Predicate<PDDocument> = Predicate { true },
    var pagePredicate: Predicate<Int> = Predicate { true },
    var visible: Boolean = true
)