package runstatic.stools.entity.table

import runstatic.stools.entity.view.GlobalConfig
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
    override var id: Int,
    @Column(name = "`key`", nullable = false)
    override var key: String,
    @Column(name = "`value`")
    override var value: String
) : GlobalConfig