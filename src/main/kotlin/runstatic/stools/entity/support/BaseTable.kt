package runstatic.stools.entity.support

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author chenmoand
 */
@MappedSuperclass
abstract class BaseTable<ID : Serializable> : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: ID? = null

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    protected val createTime: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    protected var updateTime: LocalDateTime? = null
}