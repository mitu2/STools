package runstatic.stools.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import runstatic.stools.entity.table.AuthorityTable

/**
 *
 * @author chenmoand
 */
@Repository
interface AuthorityRepository : JpaRepositoryImplementation<AuthorityTable, Long> {

    @Query("""
        FROM AuthorityTable AS at
        LEFT JOIN UserAuthorityTable AS urt 
        ON urt.authorityId = at.id
        AND urt.userId = :userId
        WHERE urt.validPeriod is null 
            or urt.validPeriod < current_date 
    """)
    fun findByUserId(userId: Long): List<AuthorityTable>

}