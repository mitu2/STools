package runstatic.stools.entity.table

import runstatic.stools.entity.support.BaseTable
import runstatic.stools.entity.view.UserAuthority
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_authority", indexes = [])
class UserAuthorityTable(
    @Column(name = "user_id", nullable = false)
    override var userId: Long,
    @Column(name = "authority_id", nullable = false)
    override var authorityId: Long,
    override var validPeriod: LocalDateTime?,
) : BaseTable<Long>(), UserAuthority {

}