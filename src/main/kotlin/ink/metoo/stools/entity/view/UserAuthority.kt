package ink.metoo.stools.entity.view

import ink.metoo.stools.constant.ValidGroups
import java.time.LocalDateTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

/**
 *
 * @author chenmoand
 */
interface UserAuthority {

    var userId: Long
        @Min(value = 1L, message = "不是合法的用户ID", groups = [ValidGroups.First::class])
        get

    var authorityId: Long
        @NotBlank(message = "权限不能为空", groups = [ValidGroups.First::class])
        get

    var validPeriod: LocalDateTime?
}