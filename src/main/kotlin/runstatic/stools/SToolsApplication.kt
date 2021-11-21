package runstatic.stools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
class SToolsApplication

fun main(args: Array<String>) {
    runApplication<SToolsApplication>(*args) {
        addListeners(ApplicationPidFileWriter())
    }
}
