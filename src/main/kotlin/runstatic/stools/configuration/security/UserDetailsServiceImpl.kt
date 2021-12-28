package runstatic.stools.configuration.security

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import runstatic.stools.entity.table.UserTable
import runstatic.stools.service.AuthorityService
import runstatic.stools.service.UserService

/**
 *
 * @author chenmoand
 */
@Service
class UserDetailsServiceImpl @Autowired constructor(
    private val userService: UserService,
    private val authorityService: AuthorityService
) : UserDetailsService {


    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrBlank()) {
            throw UsernameNotFoundException("username must not blank!")
        }
        val entity: UserTable =
            userService.getUserByAccount(username) ?: throw UsernameNotFoundException("Not obtained by username!")

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

            override fun isAccountNonLocked(): Boolean = entity.status == 2

            override fun isCredentialsNonExpired(): Boolean = true

            override fun isEnabled(): Boolean = true

        }
    }

}