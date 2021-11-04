package runstatic.stools.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import runstatic.stools.entity.table.ShortUrlTable
import runstatic.stools.exception.NotFountShortUrlException
import runstatic.stools.repository.ShortUrlRepository
import runstatic.stools.service.ShortUrlService
import kotlin.jvm.Throws

@Service
class ShortUrlServiceImpl @Autowired constructor(
    private val shortUrlRepository: ShortUrlRepository
) : ShortUrlService {

    @Transactional
    override fun saveShortUrl(shortUrlTable: ShortUrlTable): ShortUrlTable = shortUrlRepository.save(shortUrlTable)

    @Throws(NotFountShortUrlException::class)
    @Cacheable(cacheNames = ["shortUrl:router"])
    override fun findShortUrlByRouter(router: String) = shortUrlRepository.findByRouter(router) ?: throw NotFountShortUrlException("find ShortUrl $router not null")


}