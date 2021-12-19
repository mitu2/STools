package runstatic.stools.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 *
 * @author chenmoand
 */
@ConfigurationProperties(prefix = "stools")
class ApplicationProperties {

    var baseUrl: String? = null
        get() = if(field.isNullOrBlank()) "https://static.run" else field

}