package runstatic.stools.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import runstatic.stools.entity.table.GlobalConfigTable
import runstatic.stools.repository.GlobalConfigRepository
import runstatic.stools.service.GlobalConfigService

@Service
class GlobalConfigServiceImpl @Autowired constructor(
    private val globalConfigRepository: GlobalConfigRepository
) : GlobalConfigService {

    override fun getValue(key: String, defaultValue: String?): String? =
        globalConfigRepository.findByKey(key)?.value ?: defaultValue

    @Transactional
    override fun setValue(key: String, value: String?) {
        val entity = globalConfigRepository.findByKey(key)?.apply {
            this.value = value
        } ?: GlobalConfigTable(
            key = key, value = value
        )
        globalConfigRepository.save(entity)


    }

}