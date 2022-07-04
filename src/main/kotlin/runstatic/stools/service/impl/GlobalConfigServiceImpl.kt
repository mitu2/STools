package runstatic.stools.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
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

    @Cacheable("config")
    override fun getEntityByKey(key: String): GlobalConfigTable? = globalConfigRepository.findByKey(key)


    @Cacheable("config")
    override fun getEntityById(id: Int): GlobalConfigTable? = globalConfigRepository.findById(id).orElse(null)

    override fun getValue(key: String, defaultValue: String?): String? = getEntityByKey(key)?.value ?: defaultValue

    @CachePut("config")
    override fun saveConfig(entity: GlobalConfigTable) = globalConfigRepository.save(entity)


}