package runstatic.stools.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 *
 * @author chenmoand
 */
internal class RandomUtilTest {

    private val logger = useSlf4jLogger()

    @Test
    fun doRandomString() {
        val randomString = RandomUtil.randomString()
        assertNotNull(randomString)
        logger.info(randomString)
    }

}