package runstatic.stools.controller

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import org.hibernate.validator.constraints.Range
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import runstatic.stools.logging.useSlf4jLogger
import java.io.ByteArrayOutputStream
import javax.annotation.security.PermitAll
import javax.validation.constraints.NotBlank


/**
 *
 * @author chenmoand
 */
@RequestMapping("barcodeTool")
@RestController
@PermitAll
class BarcodeToolController {

    val logger = useSlf4jLogger()

    @GetMapping(path = ["generate"], produces = ["image/png"])
    fun generate(
        @Validated @ModelAttribute param: BarcodeToolParams.GenerateParam,
    ): ByteArray {
        val writer = MultiFormatWriter()
        val bitMatrix =
            writer.encode(param.text, param.type ?: BarcodeFormat.QR_CODE, param.width ?: 200, param.height ?: 200)
        val outputStream = ByteArrayOutputStream()
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)
        return outputStream.toByteArray()
    }

}


sealed interface BarcodeToolParams {

    data class GenerateParam constructor(
        @field:NotBlank
        var text: String,
        var type: BarcodeFormat?,
        @field:Range(min = 20, max = 4096)
        var width: Int?,
        @field:Range(min = 20, max = 4096)
        var height: Int?,
    ) : BarcodeToolParams

}