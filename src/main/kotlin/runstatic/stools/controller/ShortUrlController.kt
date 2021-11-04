package runstatic.stools.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import runstatic.stools.entity.table.ShortUrlTable
import runstatic.stools.service.ShortUrlService
import runstatic.stools.util.terminate

/**
 *
 * @author chenmoand
 */
@RestController
@RequestMapping("shortUrl")
class ShortUrlController @Autowired constructor(
    private val shortUrlService: ShortUrlService
) {

    @PutMapping()
    fun addShortUrl(shortUrlTable: ShortUrlTable): ShortUrlTable {
        if(shortUrlTable.id != null) {
            return terminate {  }
        }
        return shortUrlService.saveShortUrl(shortUrlTable)
    }

}