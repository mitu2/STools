package runstatic.stools.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import runstatic.stools.entity.table.ShortUrlTable
import runstatic.stools.repository.ShortUrlRepository
import runstatic.stools.service.ShortUrlService
import runstatic.stools.util.RandomStringUtil

@Service
class ShortUrlServiceImpl @Autowired constructor(
    private val shortUrlRepository: ShortUrlRepository
) : ShortUrlService {

    @Cacheable(cacheNames = ["shor-url:router"])
    override fun getShortUrlByRouter(router: String) = shortUrlRepository.findByRouter(router)


    @Transactional
    override fun randomShortUrl(url: String): String {
        val entity = ShortUrlTable(
            url = url,
            router = RandomStringUtil.randomString(),
        )
        return shortUrlRepository.save(entity).router
    }


}