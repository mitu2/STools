package runstatic.stools.repository

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import runstatic.stools.entity.table.CodeTable

/**
 *
 * @author chenmoand
 */
@Repository
interface CodeRepository : JpaRepositoryImplementation<CodeTable, Long> {
    fun findByUuid(uuid: String): CodeTable?

}