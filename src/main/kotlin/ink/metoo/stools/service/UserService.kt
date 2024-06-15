package ink.metoo.stools.service

import ink.metoo.stools.entity.table.UserTable

/**
 *
 * @author chenmoand
 */
interface UserService {

    fun saveUser(userTable: UserTable): UserTable

    fun getUserByAccount(account: String): UserTable?

    fun getUserById(id: Long): UserTable?

}