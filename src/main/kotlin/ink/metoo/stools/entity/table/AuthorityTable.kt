package ink.metoo.stools.entity.table

import ink.metoo.stools.entity.support.BaseTable
import ink.metoo.stools.entity.view.Authority
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