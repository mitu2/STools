package runstatic.stools.controller.api

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.Range
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import runstatic.stools.util.RandomStringUtil
import runstatic.stools.util.VerifyCodeUtil
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.annotation.security.PermitAll
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.NotBlank

/**
 * @author chenmoand
 */
@RestController
@RequestMapping(path = ["api/verify-code"])
@PermitAll
class VerifyCodeController {

    @GetMapping(produces = ["image/png"])
    fun generate(@Validated param: VerifyCodeParam, response: HttpServletResponse): ByteArray {
        val image = BufferedImage(param.width, param.height, BufferedImage.TYPE_INT_RGB)
        VerifyCodeUtil.drawCodeToImage(image, code = param.code)
        response.addHeader("Verify-Code", param.code)
        return ByteArrayOutputStream().apply {
            ImageIO.write(image, "png", this)
        }.toByteArray()
    }

    class VerifyCodeParam constructor(
        @field:Length(min = 2, max = 12)
        @field:NotBlank
        val code: String = RandomStringUtil.randomString(4, 4),
        @field:Range(min = 100, max = 4096)
        val width: Int = 200,
        @field:Range(min = 30, max = 4096)
        val height: Int = 60
    )

}