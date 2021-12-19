package runstatic.stools.entity.table

import runstatic.stools.entity.support.BaseTable
import runstatic.stools.entity.view.ShortUrl
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
@Table(name = "short_url", indexes = [Index(name = "router_index", columnList = "router", unique = true)])
class ShortUrlTable(
    @Column(name = "url", nullable = false, length = 5000)
    override var url: String,
    @Column(name = "router", nullable = false, length = 25)
    override var router: String,
    @Column(name = "expireDate")
    override var expireDate: LocalDateTime? = null,
) : BaseTable<Long>(), ShortUrl