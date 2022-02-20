package runstatic.stools.configuration.event

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.crypto.password.PasswordEncoder
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
//    private val jdbcTokenRepositoryImpl: JdbcTokenRepositoryImpl,
    private val properties: SToolsProperties,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
//    private val buildProperties: BuildProperties
) : ApplicationListener<ApplicationReadyEvent> {

    private val logger = useSlf4jLogger()

    private val mapper: ObjectMapper = builder.createXmlMapper(false).build()


    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        doFirstEnableServer()
        recordServerStartNumber()
        initAdmin()
    }

    fun doFirstEnableServer() {
        val isFirst = globalConfigService.getValue(GlobalConfigKeys.IS_FIRST, "true").toBoolean()
        if (isFirst) {
            globalConfigService[GlobalConfigKeys.IS_FIRST] = "false"
        }

    }

    fun initAdmin() {
        val id = globalConfigService[GlobalConfigKeys.ADMIN_ID]?.toLongOrNull()

        val (enabled, username, password, email) = properties.admin

        var user: UserTable? = null


        if (id == null || (userService.getUserById(id))?.apply { user = this } == null) {
            globalConfigService[GlobalConfigKeys.ADMIN_ID] = userService.saveUser(
                UserTable(
                    account = username,
                    password = passwordEncoder.encode(password),
                    email = email,
                    status = if (enabled) 1 else 2
                )
            ).id.toString()
            return
        }

        user?.let {
            val isAdminNotEnabled = if (enabled) it.status != 1 else false
            if (!passwordEncoder.matches(
                    password,
                    it.password
                ) || it.email != email || it.account != username || isAdminNotEnabled
            ) {
                it.account = username
                it.password = passwordEncoder.encode(password)
                it.email = email
                it.status = if (enabled) 1 else 2
                userService.saveUser(it)
            }
        }


    }

    fun recordServerStartNumber() {
        val newRunNumber = (NumberUtils.toLong(globalConfigService[GlobalConfigKeys.RUN_NUMBERS]) + 1).toString()
        globalConfigService[GlobalConfigKeys.RUN_NUMBERS] = newRunNumber
        logger.info { "Stools launches $newRunNumber, Have a nice day" }
    }

}
