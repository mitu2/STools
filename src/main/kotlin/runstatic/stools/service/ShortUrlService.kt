package runstatic.stools.service

import runstatic.stools.entity.table.ShortUrlTable

/**
 *
 * @author chenmoand
 */
interface ShortUrlService {

    fun getShortUrlByRouter(router: String): ShortUrlTable?

    fun randomShortUrl(url: String): String

}