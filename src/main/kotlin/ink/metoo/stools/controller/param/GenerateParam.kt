package ink.metoo.stools.controller.param

import com.google.zxing.BarcodeFormat
import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotBlank

class GenerateParam constructor(
    @field:NotBlank
    var text: String,
    var type: BarcodeFormat = BarcodeFormat.QR_CODE,
    @field:Range(min = 20, max = 4096)
    var width: Int = 200,
    @field:Range(min = 20, max = 4096)
    var height: Int = 200,
) : Param