package ink.metoo.stools.configuration.security

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ink.metoo.stools.configuration.SToolsProperties
import ink.metoo.stools.entity.table.UserTable
import ink.metoo.stools.service.AuthorityService
import ink.metoo.stools.service.UserService

/**
 *
 * @author chenmoand
 */
@Service
class UserDetailsServiceImpl @Autowired constructor(
    private val userService: UserService,
    private val authorityService: AuthorityService,
    private val properties: SToolsProperties,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {


    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrBlank()) {
            throw UsernameNotFoundException("username must not blank!")
        }
        val adminProperties = properties.admin
        if (adminProperties.enabled && adminProperties.username == username) {
            return User(adminProperties.username, passwordEncoder.encode(adminProperties.password), emptyList())
        }

        val entity: UserTable =
            userService.getUserByAccount(username) ?: throw UsernameNotFoundException("not obtained by username!")

        return object : UserDetails {

            override fun getAuthorities(): List<GrantedAuthority> = entity.id?.run {
                authorityService
                    .findByUserId(this)
                    .map {
                        GrantedAuthority {
                            it.value
                        }
                    }
            } ?: emptyList()


            @JsonIgnore
            override fun getPassword(): String = entity.password

            override fun getUsername(): String = entity.nickname

            override fun isAccountNonExpired(): Boolean = true

            override fun isAccountNonLocked(): Boolean = entity.status != 2

            override fun isCredentialsNonExpired(): Boolean = true

            override fun isEnabled(): Boolean = true

        }
    }

}