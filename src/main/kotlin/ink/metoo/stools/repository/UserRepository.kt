package ink.metoo.stools.repository

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import ink.metoo.stools.entity.table.UserTable

/**
 *
 * @author chenmoand
 */
@Repository
interface UserRepository : JpaRepositoryImplementation<UserTable, Long> {

    fun findByAccount(account: String): UserTable?

}