package runstatic.stools.service.exception

import org.junit.jupiter.api.Test

/**
 *
 * @author chenmoand
 */
class ServiceNotCompletedInfoTest {

    @Test
    fun mapTest() {
        val map = hashMapOf<String, String>()
        map["name"] = "abc"
        assert(map.size > 0)
    }

}