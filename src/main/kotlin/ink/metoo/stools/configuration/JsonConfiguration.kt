package ink.metoo.stools.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

/**
 *
 * @author chenmoand
 */
@Configuration
class JsonConfiguration {

    @Bean
    @ConditionalOnBean(value = [ObjectMapper::class])
    fun defaultObjectMapper(builder: Jackson2ObjectMapperBuilder) = builder.createXmlMapper(false).build<ObjectMapper>()


}