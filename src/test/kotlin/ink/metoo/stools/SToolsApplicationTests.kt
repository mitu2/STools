package ink.metoo.stools

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import ink.metoo.stools.logging.useSlf4jLogger

@SpringBootTest
class SToolsApplicationTests {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    val logger = useSlf4jLogger()

    @Test
    fun contextLoads() {
    }


    @Test
    fun testPassword() {
        val encode = passwordEncoder.encode("user")
        logger.info(encode)
    }
}
