package ink.metoo.stools.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ink.metoo.stools.logging.useSlf4jLogger
import java.util.concurrent.CountDownLatch

/**
 *
 * @author chenmoand
 */
internal class RandomStringUtilTest {

    private val logger = useSlf4jLogger()

    @Test
    fun execRandomString() {
        val randomString = RandomStringUtil.randomString()
        assertNotNull(randomString)
        logger.info(randomString)
    }

    @Test
    fun testManyRandomString() {
        val strArray = arrayListOf<String>()
        for (i in 1..1000) {
            val randomString = RandomStringUtil.randomString()
            if(strArray.contains(randomString)) {
                logger.error(randomString)
                break
            }
            logger.info(randomString)
            strArray.add(randomString)
        }
    }

    @Test
    fun testManyThreadRandomString() {
        val maxThread = 10
        val latch = CountDownLatch(maxThread - 1)
        for (i in 1 until maxThread) {
            Thread {
                val randomString = RandomStringUtil.randomString()
                logger.info("Thread index $i $randomString")
                latch.countDown()
            }.start()
        }

        latch.await()
    }


}