package runstatic.stools.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import runstatic.stools.repository.GlobalConfigRepository
import runstatic.stools.service.GlobalConfigService

@Service
class GlobalConfigServiceImpl @Autowired constructor(
    private val globalConfigRepository: GlobalConfigRepository
) : GlobalConfigService {

    override fun getValue(key: String, _default: String?): String? = globalConfigRepository.findByKey(key)?.value ?: _default

}