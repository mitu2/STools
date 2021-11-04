package runstatic.stools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SToolsApplication

fun main(args: Array<String>) {
    runApplication<SToolsApplication>(*args)
}
