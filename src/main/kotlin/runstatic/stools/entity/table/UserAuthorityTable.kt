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
    id: Long?,
    @Column(name = "user_id", nullable = false)
    override var userId: Long,
    @Column(name = "authority_id", nullable = false)
    override var authorityId: Long,
    override var validPeriod: LocalDateTime?,
    createTime: LocalDateTime,
    updateTime: LocalDateTime
) : BaseTable<Long>(id, createTime, updateTime), UserAuthority {

}