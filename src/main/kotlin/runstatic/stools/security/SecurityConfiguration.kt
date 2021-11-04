package runstatic.stools.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.*
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
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
    fun persistentTokenRepository(): PersistentTokenRepository {
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


    override fun configure(http: HttpSecurity): Unit = http.run {

        val handler = fun(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
            val callback: String? = request.getParameter("callback")
            if (callback.isNullOrBlank()) {
                response.characterEncoding = "utf-8"
                response.contentType = "application/json;charset=utf-8"
                response.writer.print("{\"success\": true}")
                return
            }
            response.sendRedirect(callback)
        }

        cors().disable()

        logout { self ->
            self.logoutUrl("/logout")
                .addLogoutHandler(handler)
                .permitAll()
        }

        formLogin { self ->
            self
                .loginPage("/login")
                .loginProcessingUrl("/api/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .successHandler(handler)
                .failureHandler { request: HttpServletRequest?, response: HttpServletResponse, exception: AuthenticationException? ->
                    response.status = 400
                    response.characterEncoding = "utf-8"
                    response.contentType = "application/json;charset=utf-8"
                    response.writer.print("{\"success\": false}")
                }
                .permitAll()
        }


        rememberMe { self ->
            self.tokenValiditySeconds(TimeUnit.DAYS.toSeconds(5L).toInt())
                .userDetailsService(userDetailsService)
                .rememberMeParameter("remember-me")
        }

        authorizeRequests { self ->
            self.antMatchers("/static/**", "/web-socket/**", "/api/**", "/webjars/**")
                .permitAll()

        }

    }

    @Bean(name = ["myAuthenticationManager"])
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

}