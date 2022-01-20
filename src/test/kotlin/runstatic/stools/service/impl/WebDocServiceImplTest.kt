package runstatic.stools.service.impl

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import runstatic.stools.logging.info
import runstatic.stools.logging.useSlf4jLogger

/**
 *
 * @author chenmoand
 */
@SpringBootTest
internal class WebDocServiceImplTest {

    @Autowired
    lateinit var webDocServiceImpl: WebDocServiceImpl

    val logger = useSlf4jLogger()

    @Test
    fun testGetMavenLatestVersion() {
        val latestVersion = webDocServiceImpl.getMavenLatestVersion("org.springframework", "spring-aop")
        logger.info { latestVersion }
    }

}