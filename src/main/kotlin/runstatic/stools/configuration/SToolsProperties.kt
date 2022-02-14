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

    var webDocResources = hashMapOf<String, String>()

    companion object {
        val DEFAULT_WORK_FOLDER = "${System.getProperty("user.home")}/.STools"
        const val DEFAULT_BASE_UTL = "https://static.run"
    }
}