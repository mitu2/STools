package runstatic.stools.controller.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import runstatic.stools.util.RandomStringUtil
import runstatic.stools.util.VerifyCodeUtil
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

/**
 * @author chenmoand
 */
@RestController
@RequestMapping(path = ["verify-code"])
class VerifyCodeController {

    @GetMapping(produces = ["image/png"])
    fun generate(param: VerifyCodeParam): ByteArray {
        val image = BufferedImage(param.width, param.height, BufferedImage.TYPE_INT_RGB)
        VerifyCodeUtil.drawCodeToImage(image, code = param.code)
        return ByteArrayOutputStream().apply {
            ImageIO.write(image, "png", this)
        }.toByteArray()
    }

    data class VerifyCodeParam constructor(
        val code: String = RandomStringUtil.randomString(4, 4),
        val width: Int = 200,
        val height: Int = 60
    )

}