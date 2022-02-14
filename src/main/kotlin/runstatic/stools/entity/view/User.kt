package runstatic.stools.entity.view

import org.hibernate.validator.constraints.Range
import runstatic.stools.constant.RegexpCosts.ACCOUNT_REGEXP
import runstatic.stools.constant.RegexpCosts.PASSWORD_REGEXP
import runstatic.stools.constant.ValidGroups
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

/**
 *
 * @author chenmoand
 */
interface User {

    var account: String
        @Pattern(
            regexp = ACCOUNT_REGEXP,
            message = "用户名正则，5到20位（字母，数字，下划线，减号）",
            groups = [ValidGroups.Second::class]
        )
        get

    var nickname: String
        @NotBlank(groups = [ValidGroups.First::class])
        get

    var status: Int
        @Range(min = 0, max = 10, groups = [ValidGroups.First::class])
        get

    var password: String
        @Pattern(
            regexp = PASSWORD_REGEXP,
            message = "密码强度正则，最少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符",
            groups = [ValidGroups.Second::class]
        )
        get

    var email: String?
        @Email(groups = [ValidGroups.First::class])
        get

}