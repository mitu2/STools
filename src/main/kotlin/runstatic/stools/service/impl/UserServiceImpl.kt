package runstatic.stools.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import runstatic.stools.entity.table.UserTable
import runstatic.stools.repository.UserRepository
import runstatic.stools.service.UserService

@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
) : UserService {


    @Transactional
    override fun addUser(userTable: UserTable): UserTable = userRepository.save(userTable)

    @Cacheable(cacheNames = ["user:account"])
    override fun getUserByAccount(account: String): UserTable? = userRepository.findByAccount(account)

}