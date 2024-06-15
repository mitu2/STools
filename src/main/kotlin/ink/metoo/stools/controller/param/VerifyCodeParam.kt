package ink.metoo.stools.controller.param

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.Range
import ink.metoo.stools.util.RandomStringUtil
import javax.validation.constraints.NotBlank

class VerifyCodeParam constructor(
    @field:Length(min = 2, max = 12)
    @field:NotBlank
    val code: String = RandomStringUtil.randomString(4, 4),
    @field:Range(min = 100, max = 4096)
    val width: Int = 200,
    @field:Range(min = 30, max = 4096)
    val height: Int = 60
) : Param