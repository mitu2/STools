package runstatic.stools.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import runstatic.stools.entity.table.AuthorityTable
import runstatic.stools.entity.table.UserTable

/**
 *
 * @author chenmoand
 */
@Repository
interface UserRepository : JpaRepositoryImplementation<UserTable, Long> {

    fun findByAccount(account: String): UserTable?

}