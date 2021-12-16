package runstatic.stools.util

import org.springframework.util.Assert
import java.util.concurrent.ThreadLocalRandom

/**
 *
 * @author chenmoand
 */
object RandomUtil {

    private val random = ThreadLocalRandom.current()

    @Suppress("SpellCheckingInspection")
    const val RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    fun randomString(minLength: Int = 4, maxLength: Int = 12): String {
        Assert.state(minLength > 0, "minLength must be greater than 0")
        Assert.state(maxLength > 0, "minLength must be greater than 0")
        Assert.state(maxLength > minLength, "maxLength must be greater than minLength")
        val length = random.nextInt(maxLength - minLength) + minLength
        val chars = CharArray(length)
        for (i in 0 until length) {
            chars[i] = RANDOM_STR[random.nextInt(RANDOM_STR.length - 1)]
        }
        return chars.concatToString()
    }

}