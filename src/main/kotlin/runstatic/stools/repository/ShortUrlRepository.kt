package runstatic.stools.repository

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import runstatic.stools.entity.table.ShortUrlTable

/**
 *
 * @author chenmoand
 */
@Repository
interface ShortUrlRepository : JpaRepositoryImplementation<ShortUrlTable, Long> {

    fun findByRouter(router: String): ShortUrlTable?

}