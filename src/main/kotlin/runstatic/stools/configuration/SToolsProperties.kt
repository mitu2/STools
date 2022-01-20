package runstatic.stools.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 *
 * @author chenmoand
 */
@ConfigurationProperties(prefix = "stools")
class SToolsProperties {

    var baseUrl: String = DEFAULT_BASE_UTL

    var workFolder: String = DEFAULT_WORK_FOLDER

    companion object {
        private val USER_DIR: String = System.getProperty("user.home")
        val DEFAULT_WORK_FOLDER = "${USER_DIR}/.STools"
        const val DEFAULT_BASE_UTL = "https://static.run"
    }
}