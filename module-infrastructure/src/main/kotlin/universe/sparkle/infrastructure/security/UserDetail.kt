package universe.sparkle.infrastructure.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import universe.sparkle.domain.model.AuthenticationToken
import universe.sparkle.domain.model.Couple
import java.util.stream.Collectors

class UserDetail constructor(
    val id: Long,
    private val nickname: String?,
    val image: String?,
    val couple: Couple?,
    private val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority("User")),
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities.stream().collect(
            Collectors.toSet(),
        )
    }

    override fun getPassword(): String? = null

    override fun getUsername(): String? = nickname

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    companion object {
        fun of(user: AuthenticationToken): UserDetail {
            return UserDetail(
                id = user.id,
                nickname = user.nickname,
                image = user.image,
                couple = user.couple
            )
        }
    }
}
