package runstatic.stools.entity.table

import org.hibernate.annotations.ColumnDefault
import runstatic.stools.entity.support.BaseTable
import runstatic.stools.entity.view.User
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

/**
 *
 * @author chenmoand
 */
@Entity
@Table(name = "user", indexes = [Index(name = "account_index", columnList = "account", unique = true)])
class UserTable(
    @Column(name = "account", length = 20, nullable = false)
    @ColumnDefault("''")
    override var account: String,
    @Column(name = "password", nullable = false)
    override var password: String,
    @Column(name = "nickname")
    @ColumnDefault("''")
    override var nickname: String = "",
    @Column(name = "status", length = 1, nullable = false)
    @ColumnDefault("1")
    override var status: Int = 1,
    @Column(name = "email")
    override var email: String? = null,
) : BaseTable<Long>(), User {

}