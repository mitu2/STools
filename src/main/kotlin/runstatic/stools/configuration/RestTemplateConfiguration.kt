package runstatic.stools.configuration

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

/**
 *
 * @author chenmoand
 */
@Configuration
class RestTemplateConfiguration {

    @Bean
    fun restTemplate() = RestTemplate(OkHttp3ClientHttpRequestFactory(okhttpClient()))


    @Bean
    fun okhttpClient() = OkHttpClient
        .Builder()
        .build()

}