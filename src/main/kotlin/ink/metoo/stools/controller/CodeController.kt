package ink.metoo.stools.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ink.metoo.stools.entity.view.Code
import ink.metoo.stools.service.CodeService

/**
 *
 * @author chenmoand
 */
@RestController
@RequestMapping(path = ["code"])
class CodeController @Autowired constructor(
    private val codeService: CodeService
) {


    @PreAuthorize("hasAnyAuthority('user:base')")
    @GetMapping(path = ["{id}"])
    fun getCodeById(@PathVariable id: Long): Code = codeService.getCodeById(id)


}