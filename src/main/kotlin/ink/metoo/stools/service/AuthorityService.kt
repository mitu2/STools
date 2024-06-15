package ink.metoo.stools.service

import ink.metoo.stools.entity.table.AuthorityTable

/**
 *
 * @author chenmoand
 */
interface AuthorityService {

    fun findByUserId(userId: Long): List<AuthorityTable>

}