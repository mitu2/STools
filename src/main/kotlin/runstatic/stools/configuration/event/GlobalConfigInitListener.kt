package runstatic.stools.configuration.event

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import runstatic.stools.constant.GlobalConfigKeys
import runstatic.stools.service.GlobalConfigService
import runstatic.stools.logging.useSlf4jLogger

/**
 *
 * @author chenmoand
 */
//@Component
class GlobalConfigInitListener @Autowired constructor(
    builder: Jackson2ObjectMapperBuilder,
    private val globalConfigService: GlobalConfigService,
    private val jdbcTokenRepositoryImpl: JdbcTokenRepositoryImpl,
) : ApplicationListener<ApplicationReadyEvent> {

    private val logger = useSlf4jLogger()

    private val mapper: ObjectMapper = builder.createXmlMapper(false).build()


    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val isFirst = globalConfigService.getValue(GlobalConfigKeys.IS_FIRST, "true").toBoolean()

        if (isFirst) {
            jdbcTokenRepositoryImpl.setCreateTableOnStartup(true)
        }

    }

}