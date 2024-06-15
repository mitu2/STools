package ink.metoo.stools.entity.view

import com.fasterxml.jackson.annotation.JsonView
import org.hibernate.validator.constraints.Length
import ink.metoo.stools.constant.JsonViews
import ink.metoo.stools.constant.ValidGroups
import javax.validation.constraints.NotBlank

/**
 *
 * @author chenmoand
 */
interface Code {

    var uuid: String
        @NotBlank(message = "uuid 不能是空或空字符串!", groups = [ValidGroups.First::class])
        @Length(min = 32, max = 32, message = "uuid 长度必须是32!", groups = [ValidGroups.First::class])
        get

    var language: String
        @NotBlank(message = "language 不能是空或空字符串!", groups = [ValidGroups.First::class])
        get

    var content: String
        @NotBlank(message = "content 不能是空或空字符串!", groups = [ValidGroups.First::class])
        get

    var version: Int
        @JsonView(JsonViews.First::class)
        get
}