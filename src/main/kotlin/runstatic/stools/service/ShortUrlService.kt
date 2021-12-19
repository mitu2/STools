package runstatic.stools.service

import runstatic.stools.entity.table.ShortUrlTable
import runstatic.stools.exception.NotFountShortUrlException
import kotlin.jvm.Throws

/**
 *
 * @author chenmoand
 */
interface ShortUrlService {

    fun findShortUrlByRouter(router: String): ShortUrlTable?

    fun randomShortUrl(url: String): String

}