package runstatic.stools.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import runstatic.stools.entity.table.AuthorityTable
import runstatic.stools.repository.AuthorityRepository
import runstatic.stools.service.AuthorityService

@Service
class AuthorityServiceImpl @Autowired constructor(
    private val authorityRepository: AuthorityRepository,
) : AuthorityService {

    override fun findByUserId(userId: Long): List<AuthorityTable> = authorityRepository.findByUserId(userId)

}