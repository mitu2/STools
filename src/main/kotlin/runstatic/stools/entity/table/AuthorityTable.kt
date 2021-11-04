package runstatic.stools.entity.table

import runstatic.stools.entity.support.BaseTable
import runstatic.stools.entity.view.Authority
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "authority")
class AuthorityTable(
    id: Long?,
    @Column(name = "description")
    override var description: String?,
    @Column(name = "value", nullable = false)
    override var value: String,
    createTime: LocalDateTime,
    updateTime: LocalDateTime
) : BaseTable<Long>(id, createTime, updateTime), Authority {

}