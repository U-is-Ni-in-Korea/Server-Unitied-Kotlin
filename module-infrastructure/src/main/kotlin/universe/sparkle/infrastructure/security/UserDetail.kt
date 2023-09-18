package universe.sparkle.infrastructure.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import universe.sparkle.domain.model.User
import java.util.stream.Collectors

class UserDetail private constructor(
    private val id: Long,
    private val nickname: String?,
    private val snsAuthCode: String,
    private val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority("User")),
    val image: String?,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities.stream().collect(
            Collectors.toSet(),
        )
    }

    override fun getPassword(): String = snsAuthCode

    override fun getUsername(): String? = nickname

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    companion object {
        fun of(user: User): UserDetail {
            return UserDetail(
                id = 0,
                nickname = user.nickname,
                snsAuthCode = user.snsAuthCode,
                image = user.image
            )
        }
    }
}
