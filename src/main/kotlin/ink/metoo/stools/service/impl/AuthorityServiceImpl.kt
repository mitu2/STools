package ink.metoo.stools.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ink.metoo.stools.entity.table.AuthorityTable
import ink.metoo.stools.repository.AuthorityRepository
import ink.metoo.stools.service.AuthorityService

@Service
class AuthorityServiceImpl @Autowired constructor(
    private val authorityRepository: AuthorityRepository,
) : AuthorityService {

    override fun findByUserId(userId: Long): List<AuthorityTable> = authorityRepository.findByUserId(userId)

}