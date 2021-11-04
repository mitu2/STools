package runstatic.stools.service

import runstatic.stools.entity.table.CodeTable

/**
 *
 * @author chenmoand
 */
interface CodeService {
    fun getCodeById(id: Long): CodeTable
    fun addCode(code: CodeTable): CodeTable
}