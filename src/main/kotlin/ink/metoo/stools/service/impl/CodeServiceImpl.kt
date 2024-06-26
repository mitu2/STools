package ink.metoo.stools.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ink.metoo.stools.entity.table.CodeTable
import ink.metoo.stools.logging.useSlf4jLogger
import ink.metoo.stools.repository.CodeRepository
import ink.metoo.stools.service.CodeService


@Service
class CodeServiceImpl @Autowired constructor(
    val codeRepository: CodeRepository
) : CodeService {

    val log = useSlf4jLogger()

    @Cacheable(cacheNames = ["code"])
    override fun getCodeById(id: Long): CodeTable {
        assert(id > 0) { "Get Code id must > 0!" }
        return codeRepository.getById(id)
    }

    @Transactional
    override fun addCode(code: CodeTable): CodeTable = codeRepository.save(code)


}
