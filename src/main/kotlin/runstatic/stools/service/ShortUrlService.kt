package runstatic.stools.service

import runstatic.stools.entity.table.ShortUrlTable
import runstatic.stools.exception.NotFountShortUrlException
import kotlin.jvm.Throws

/**
 *
 * @author chenmoand
 */
interface ShortUrlService {

    fun saveShortUrl(shortUrlTable: ShortUrlTable): ShortUrlTable

    @Throws(NotFountShortUrlException::class)
    fun findShortUrlByRouter(router: String): ShortUrlTable

}