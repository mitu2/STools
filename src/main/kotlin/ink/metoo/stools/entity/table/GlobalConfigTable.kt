package ink.metoo.stools.entity.table

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import ink.metoo.stools.entity.view.GlobalConfig
import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author chenmoand
 */
@Table(name = "global_config", indexes = [Index(name = "key_index", columnList = "`key`", unique = true)])
@Entity
class GlobalConfigTable(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Int? = null,
    @Column(name = "`key`", nullable = false)
    override var key: String,
    @Column(name = "`value`")
    override var value: String?,
    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    val createTime: LocalDateTime? = null,
    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    var updateTime: LocalDateTime? = null
) : GlobalConfig