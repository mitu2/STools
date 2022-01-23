package runstatic.stools.service

import runstatic.stools.entity.table.ShortUrlTable

/**
 *
 * @author chenmoand
 */
interface ShortUrlService {

    fun findShortUrlByRouter(router: String): ShortUrlTable?

    fun randomShortUrl(url: String): String

}