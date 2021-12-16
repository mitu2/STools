package runstatic.stools.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import runstatic.stools.entity.table.ShortUrlTable
import runstatic.stools.exception.NotFountShortUrlException
import runstatic.stools.repository.ShortUrlRepository
import runstatic.stools.service.ShortUrlService
import runstatic.stools.util.RandomUtil
import java.util.concurrent.ThreadLocalRandom
import kotlin.jvm.Throws
import kotlin.random.asKotlinRandom

@Service
class ShortUrlServiceImpl @Autowired constructor(
    private val shortUrlRepository: ShortUrlRepository
) : ShortUrlService {


    @Transactional
    override fun save(shortUrlTable: ShortUrlTable): ShortUrlTable = shortUrlRepository.save(shortUrlTable)

    @Cacheable(cacheNames = ["shortUrl:router"])
    override fun findShortUrlByRouter(router: String) = shortUrlRepository.findByRouter(router)


    @Transactional
    override fun randomShortUrl(url: String): String {
        val entity = ShortUrlTable(
            url = url,
            router = RandomUtil.randomString(),
        )
        return shortUrlRepository.save(entity).router
    }


}