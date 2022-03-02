package runstatic.stools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.filter.FilterPosition

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
@EnableConfigurationProperties(SToolsProperties::class)
@ServletComponentScan(basePackageClasses = [FilterPosition::class])
class SToolsApplication

fun main(args: Array<String>) {
    runApplication<SToolsApplication>(*args) {
        addListeners(ApplicationPidFileWriter())
    }
}
