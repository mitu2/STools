package ink.metoo.stools.service.impl

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert
import ink.metoo.stools.SToolsApplication
import ink.metoo.stools.service.GlobalConfigService
import ink.metoo.stools.service.set
import ink.metoo.stools.service.get

/**
 *
 * @author chenmoand
 */
@SpringBootTest(classes = [ink.metoo.stools.SToolsApplication::class])
internal class GlobalConfigServiceImplTest {

    @Autowired
    private lateinit var globalConfigService: GlobalConfigService

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Test
    fun testSetConfig() {
        val value = "NONE"
        globalConfigService["test_field"] = value
        throw NullPointerException()
        Assertions.assertEquals(globalConfigService.getValue("test_field"), value)
    }

}