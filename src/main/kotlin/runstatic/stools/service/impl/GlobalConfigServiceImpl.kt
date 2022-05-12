package runstatic.stools.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import runstatic.stools.entity.table.GlobalConfigTable
import runstatic.stools.repository.GlobalConfigRepository
import runstatic.stools.service.GlobalConfigService

@Service
class GlobalConfigServiceImpl @Autowired constructor(
    private val globalConfigRepository: GlobalConfigRepository,
    val builder: Jackson2ObjectMapperBuilder
) : GlobalConfigService {

    override val mapper: ObjectMapper by lazy {
        builder.createXmlMapper(false).build()
    }

    override fun getEntityByKey(key: String): GlobalConfigTable? = globalConfigRepository.findByKey(key)

    override fun getEntityById(id: Int): GlobalConfigTable? = globalConfigRepository.getById(id)

    override fun getValue(key: String, defaultValue: String?): String? = getEntityByKey(key)?.value ?: defaultValue

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