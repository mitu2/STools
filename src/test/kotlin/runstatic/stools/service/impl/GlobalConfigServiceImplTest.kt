package runstatic.stools.service.impl

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert
import runstatic.stools.SToolsApplication
import runstatic.stools.service.GlobalConfigService
import runstatic.stools.service.set
import runstatic.stools.service.get

/**
 *
 * @author chenmoand
 */
@SpringBootTest(classes = [SToolsApplication::class])
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