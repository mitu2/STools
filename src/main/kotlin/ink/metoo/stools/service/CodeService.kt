package ink.metoo.stools.service

import ink.metoo.stools.entity.table.CodeTable

/**
 *
 * @author chenmoand
 */
interface CodeService {

    fun getCodeById(id: Long): CodeTable

    fun addCode(code: CodeTable): CodeTable

}