package runstatic.stools.entity.support

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author chenmoand
 */
@MappedSuperclass
abstract class BaseTable<ID : Serializable> : Persistable<ID>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected val id: ID? = null
        @JvmName("id")
        get

    override fun getId(): ID? = id

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    protected val createTime: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    protected var updateTime: LocalDateTime? = null

    override fun isNew(): Boolean = id == null

}