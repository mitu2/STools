package ink.metoo.stools.service.impl

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.annotation.EnableCaching
import ink.metoo.stools.SToolsApplication
import runstatic.stools.logging.info
import runstatic.stools.logging.useSlf4jLogger
import java.net.URLConnection
import java.nio.file.Files
import java.nio.file.Paths

/**
 *
 * @author chenmoand
 */
@SpringBootTest(classes = [ink.metoo.stools.SToolsApplication::class])
@EnableCaching
internal class WebDocServiceImplTest {

    @Autowired
    lateinit var webDocServiceImpl: WebDocServiceImpl


    val logger = useSlf4jLogger()

    @Test
    fun testGetMavenLatestVersion() {
        val latestVersion = webDocServiceImpl.getLatestVersion("maven", "org.springframework", "spring-aop")
        logger.info { latestVersion }
    }

    @Test
    fun testGetFileMineType() {
        logger.info(URLConnection.guessContentTypeFromName("css/style.css"))
        logger.info(Files.probeContentType(Paths.get("css/style.css")))
        logger.info(Files.probeContentType(Paths.get("index.html")))
    }

    @Test
    fun testCacheGetMavenLatestVersion() {
        logger.info(webDocServiceImpl.getLatestVersion("maven", "org.springframework", "spring-aop"))
        logger.info(webDocServiceImpl.getLatestVersion("maven", "org.springframework", "spring-aop"))
        logger.info(webDocServiceImpl.getLatestVersion("maven", "org.springframework", "spring-aop"))
    }

}