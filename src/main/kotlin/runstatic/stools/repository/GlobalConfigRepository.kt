package runstatic.stools.repository

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import runstatic.stools.entity.table.GlobalConfigTable

/**
 *
 * @author chenmoand
 */
@Repository
interface GlobalConfigRepository : JpaRepositoryImplementation<GlobalConfigTable, Int> {

    fun findByKey(key: String): GlobalConfigTable?

}