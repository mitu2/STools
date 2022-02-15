package runstatic.stools.configuration.event

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.info.BuildProperties
import org.springframework.context.ApplicationListener
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.stereotype.Component
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.constant.GlobalConfigKeys
import runstatic.stools.entity.table.UserTable
import runstatic.stools.logging.info
import runstatic.stools.logging.useSlf4jLogger
import runstatic.stools.service.GlobalConfigService
import runstatic.stools.service.UserService

/**
 *
 * @author chenmoand
 */
@Component
class GlobalConfigInitListener @Autowired constructor(
    builder: Jackson2ObjectMapperBuilder,
    private val globalConfigService: GlobalConfigService,
    private val jdbcTokenRepositoryImpl: JdbcTokenRepositoryImpl,
    private val properties: SToolsProperties,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val buildProperties: BuildProperties
) : ApplicationListener<ApplicationReadyEvent> {

    private val logger = useSlf4jLogger()

    private val mapper: ObjectMapper = builder.createXmlMapper(false).build()


    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        doFirstEnableServer()
        recordServerStartNumber()
    }

    fun doFirstEnableServer() {
        val isFirst = globalConfigService.getValue(GlobalConfigKeys.IS_FIRST, "true").toBoolean()
        if (isFirst) {
            // jdbcTokenRepositoryImpl.jdbcTemplate?.execute(JdbcTokenRepositoryImpl.CREATE_TABLE_SQL)
            // logger.info { "init JdbcTokenRepositoryImpl table persistent_logins" }
            globalConfigService[GlobalConfigKeys.IS_FIRST] = "false"
        }
        var (enabled, username, password, email) = properties.admin
        if (!enabled || userService.getUserByAccount(username) != null) {
            return
        }
        password = passwordEncoder.encode(password)
        userService.addUser(UserTable(account = username, password = password, email = email))
    }

    fun recordServerStartNumber() {
        val newRunNumber = (NumberUtils.toLong(globalConfigService[GlobalConfigKeys.RUN_NUMBERS]) + 1).toString()
        globalConfigService[GlobalConfigKeys.RUN_NUMBERS] = newRunNumber
        logger.info { "Stools launches $newRunNumber, Have a nice day" }
    }

}
