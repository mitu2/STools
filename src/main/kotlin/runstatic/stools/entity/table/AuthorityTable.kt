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
    @Column(name = "description")
    override var description: String?,
    @Column(name = "value", nullable = false)
    override var value: String,
) : BaseTable<Long>(), Authority {

}