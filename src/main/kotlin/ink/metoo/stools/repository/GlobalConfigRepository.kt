package ink.metoo.stools.repository

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import ink.metoo.stools.entity.table.GlobalConfigTable

/**
 *
 * @author chenmoand
 */
@Repository
interface GlobalConfigRepository : JpaRepositoryImplementation<GlobalConfigTable, Int> {

    fun findByKey(key: String): GlobalConfigTable?

}