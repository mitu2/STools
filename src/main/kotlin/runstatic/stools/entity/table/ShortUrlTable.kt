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
    id: Long?,
    @Column(name = "url", nullable = false)
    override var url: String,
    @Column(name = "router", nullable = false, length = 25)
    override var router: String,
    @Column(name = "expireDate")
    override var expireDate: LocalDateTime?,
    createTime: LocalDateTime,
    updateTime: LocalDateTime
) :
    BaseTable<Long>(id, createTime, updateTime), ShortUrl