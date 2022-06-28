package runstatic.stools.configuration.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.*
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.context.SecurityContextHolderFilter
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.filter.LoginFilter
import runstatic.stools.ui.view.admin.LoginView
import runstatic.stools.util.SecurityUtils


/**
 *
 * @author chenmoand
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
class SecurityConfiguration @Autowired constructor(
//    builder: Jackson2ObjectMapperBuilder,
//    private val dataSource: DataSource,
    private val loginFilter: LoginFilter,
    private val requestCache: CustomRequestCache,
    private val properties: SToolsProperties
) {

//    private val mapper: ObjectMapper = builder.createXmlMapper(false).build()


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http.addFilterAfter(loginFilter, SecurityContextHolderFilter::class.java)

        http.headers()
            // .contentTypeOptions()
            // .disable()
            .frameOptions().disable()

        http.csrf().disable().requestCache().requestCache(requestCache).and().requestMatchers().and()
            .authorizeRequests().antMatchers(HttpMethod.GET).permitAll()
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll().anyRequest().authenticated().and()
            .formLogin().loginPage(properties.vaadinBaseUrl + LoginView.ROUTE).permitAll()
            .loginProcessingUrl(properties.vaadinBaseUrl)
//            .failureUrl(LOGIN_FAILURE_URL) // Configure logout
//            .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL)

        return http.build()
    }

    @Bean
    fun ignoringCustomizer() = WebSecurityCustomizer { web: WebSecurity ->
        web.ignoring().antMatchers(
            "/VAADIN/**",
            "/favicon.ico",
            "/robots.txt",
            "/manifest.webmanifest",
            "/sw.js",
            "/offline-page.html",
            "/frontend/**",
            "/webjars/**",
            "/frontend-es5/**",
            "/frontend-es6/**",
            "/web-doc/**"
        )
    }

    @Bean
    @Suppress("DEPRECATION")
    fun passwordEncoder(): PasswordEncoder {
        val defEncode = "bcrypt"
        val encoders = mapOf(
            defEncode to BCryptPasswordEncoder(),
            "pbkdf2" to Pbkdf2PasswordEncoder(),
            "scrypt" to SCryptPasswordEncoder(),
            "sha256" to StandardPasswordEncoder(),
            "noop" to NoOpPasswordEncoder.getInstance()
        )
        return DelegatingPasswordEncoder(defEncode, encoders)
    }


}