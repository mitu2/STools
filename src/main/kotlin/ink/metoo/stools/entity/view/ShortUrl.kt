package ink.metoo.stools.entity.view

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL
import ink.metoo.stools.constant.ValidGroups
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

/**
 *
 * @author chenmoand
 */
interface ShortUrl {

    var url: String
        @NotBlank(message = "url不符合规则", groups = [ValidGroups.First::class])
        @URL(message = "url不符合规则", groups = [ValidGroups.First::class])
        get

    var router: String
        @NotBlank(message = "router不符合规则", groups = [ValidGroups.First::class])
        @Length(min = 4, max = 20, groups = [ValidGroups.First::class])
        get

    var expireDate: LocalDateTime?

}