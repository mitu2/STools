package runstatic.stools.service

import runstatic.stools.entity.table.UserTable

/**
 *
 * @author chenmoand
 */
interface UserService {

    fun addUser(userTable: UserTable): UserTable

    fun getUserByAccount(account: String): UserTable?

}