package ink.metoo.stools.service

import ink.metoo.stools.entity.table.ShortUrlTable

/**
 *
 * @author chenmoand
 */
interface ShortUrlService {

    fun getShortUrlByRouter(router: String): ShortUrlTable?

    fun randomShortUrl(url: String): String

}