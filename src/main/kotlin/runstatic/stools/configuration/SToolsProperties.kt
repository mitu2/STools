package runstatic.stools.configuration

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.env.Environment
import javax.annotation.PostConstruct


/**
 *
 * @author chenmoand
 */
@ConfigurationProperties(prefix = "stools")
class SToolsProperties {

    var baseUrl: String = NOT_SET

    var workFolder: String = NOT_SET

    var webDocResources = linkedMapOf<String, String>()

    var vaadinBaseUrl: String = "/"

    var admin: AdminProperties = AdminProperties()

    data class AdminProperties(
        var enabled: Boolean = false,
        var username: String = "",
        var password: String = "",
        var email: String = ""
    )


    @Autowired
    @JsonIgnore
    private lateinit var environment: Environment


    @PostConstruct
    fun init() {
        if(NOT_SET == baseUrl) {
            baseUrl = DEFAULT_BASE_UTL
        }
        if(NOT_SET == workFolder) {
            workFolder = environment.resolvePlaceholders("\${user.home}") + "/.STools"
        }
    }

    

    companion object {
        const val NOT_SET = "__NOT_SET__"
        const val DEFAULT_BASE_UTL = "https://static.run"
    }
}