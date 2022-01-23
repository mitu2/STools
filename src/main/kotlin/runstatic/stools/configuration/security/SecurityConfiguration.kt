package runstatic.stools.configuration.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.*
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import runstatic.stools.util.SecurityUtils
import javax.sql.DataSource

/**
 *
 * @author chenmoand
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
class SecurityConfiguration @Autowired constructor(
    builder: Jackson2ObjectMapperBuilder,
    private val userDetailsService: UserDetailsService,
    private val dataSource: DataSource
) : WebSecurityConfigurerAdapter() {

    private val mapper: ObjectMapper = builder.createXmlMapper(false).build()


    @Bean
    fun jdbcTokenRepositoryImpl(): JdbcTokenRepositoryImpl {
        val jdbcTokenRepositoryImpl = JdbcTokenRepositoryImpl()
        jdbcTokenRepositoryImpl.setDataSource(dataSource)
        return jdbcTokenRepositoryImpl
    }

    @Bean
    @Suppress("DEPRECATION")
    fun passwordEncoder(): PasswordEncoder {
        val encoders = hashMapOf(
            "bcrypt" to BCryptPasswordEncoder(),
            "noop" to NoOpPasswordEncoder.getInstance(),
            "pbkdf2" to Pbkdf2PasswordEncoder(),
            "scrypt" to SCryptPasswordEncoder(),
            "sha256" to StandardPasswordEncoder()
        )
        return DelegatingPasswordEncoder("bcrypt", encoders)
    }


    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/VAADIN/**",
            "/favicon.ico",
            "/robots.txt",
            "/manifest.webmanifest",
            "/sw.js",
            "/offline-page.html",
            "/frontend/**",
            "/webjars/**",
            "/frontend-es5/**", "/frontend-es6/**"
        );
    }

    override fun configure(http: HttpSecurity): Unit = http.run {

        http.headers()
            // .contentTypeOptions()
            // .disable()
            .frameOptions().disable()

        http.csrf().disable()
            .requestCache().requestCache(CustomRequestCache())
            .and().requestMatchers()
            .and().authorizeRequests()
            .antMatchers(HttpMethod.GET).permitAll()
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
            .anyRequest().authenticated()
//            .and().formLogin().loginPage(LOGIN_URL).permitAll()
//            .loginProcessingUrl(LOGIN_PROCESSING_URL)
//            .failureUrl(LOGIN_FAILURE_URL) // Configure logout
//            .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL)

    }

    @Bean(name = ["myAuthenticationManager"])
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

}