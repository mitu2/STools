package runstatic.stools.entity.table

import org.hibernate.annotations.ColumnDefault
import runstatic.stools.entity.view.Code
import runstatic.stools.entity.support.BaseTable
import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author chenmoand
 */
@Entity
@Table(name = "code", indexes = [Index(name = "uuid_index", columnList = "uuid", unique = true)])
class CodeTable(
    @Column(name = "uuid", length = 32, nullable = false)
    @ColumnDefault("''")
    override var uuid: String = "",
    @Column(name = "language", length = 20, nullable = false)
    @ColumnDefault("'txt'")
    override var language: String = "txt",
    @Column(name = "content", columnDefinition = "text", nullable = false)
    override var content: String,
    @Version
    @Column(name = "version", nullable = false)
    override var version: Int = 0,
) : BaseTable<Long>(), Code
