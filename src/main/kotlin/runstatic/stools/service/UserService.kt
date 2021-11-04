package runstatic.stools.service

import org.springframework.cache.annotation.Cacheable
import runstatic.stools.entity.table.UserTable

/**
 *
 * @author chenmoand
 */
interface UserService {
    fun addUser(userTable: UserTable): UserTable
    fun getUserByAccount(account: String): UserTable?
}