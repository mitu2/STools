package runstatic.stools.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import runstatic.stools.entity.EntityPosition
import runstatic.stools.repository.RepositoryPosition

/**
 * @author chenmoand
 * @version 0.1
 */
@EntityScan(basePackageClasses = [EntityPosition::class])
@EnableJpaRepositories(basePackageClasses = [RepositoryPosition::class])
@EnableTransactionManagement
@Configuration
@EnableCaching
class DatabaseConfiguration 