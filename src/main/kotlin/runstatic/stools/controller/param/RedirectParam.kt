package runstatic.stools.controller.param

import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank

class RedirectParam(
    @field:Range(min = 0, max = 15)
    val cycle: Int? = 0,
    @field:URL
    @field:NotBlank
    val to: String
) : Param