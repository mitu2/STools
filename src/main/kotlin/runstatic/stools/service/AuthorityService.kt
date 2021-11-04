package runstatic.stools.service

import runstatic.stools.entity.table.AuthorityTable

/**
 *
 * @author chenmoand
 */
interface AuthorityService {

    fun findByUserId(userId: Long): List<AuthorityTable>

}