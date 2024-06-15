package ink.metoo.stools.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import runstatic.stools.SToolsApplicationTests

/**
 *
 * @author chenmoand
 */
@SpringBootTest
@ComponentScan(basePackageClasses = [SToolsApplicationTests::class])
class AuthorityRepositoryTest {

    @Autowired
    lateinit var authorityRepository: AuthorityRepository

//    @Test
    @Sql("/test-sql/user.sql", config = SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
    fun onFindByUserId() {
        val result = authorityRepository.findByUserId(1)
        assert(result.isNotEmpty())
    }

}