package runstatic.stools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import runstatic.stools.configuration.SToolsProperties

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
@EnableConfigurationProperties(SToolsProperties::class)
class SToolsApplication

fun main(args: Array<String>) {
    runApplication<SToolsApplication>(*args) {
        addListeners(ApplicationPidFileWriter())
    }
}
