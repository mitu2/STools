package runstatic.stools.controller.api

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
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
@RequestMapping(path = ["api/barcode"])
@RestController
@PermitAll
class BarcodeToolController {

    val logger = useSlf4jLogger()

    @GetMapping(produces = ["image/png"])
    fun generate(
        @Validated @ModelAttribute param: GenerateParam,
    ): ByteArray {
        val bitMatrix = MultiFormatWriter()
            .encode(
                param.text, param.type, param.width, param.height,
                /* @see https://stackoverflow.com/questions/14019012/how-to-remove-white-space-on-side-qr-code-using-zxing */
                mapOf(EncodeHintType.MARGIN to 0)
            )
        return ByteArrayOutputStream().apply {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", this)
        }.toByteArray()
    }


    class GenerateParam constructor(
        @field:NotBlank
        var text: String,
        var type: BarcodeFormat = BarcodeFormat.QR_CODE,
        @field:Range(min = 20, max = 4096)
        var width: Int = 200,
        @field:Range(min = 20, max = 4096)
        var height: Int = 200,
    )

}
