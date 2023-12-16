package universe.sparkle.application.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import universe.sparkle.domain.exception.Unauthorized
import universe.sparkle.domain.usecase.ExtractTokenInputBoundary
import universe.sparkle.application.security.UserDetail

@Component
class JwtAuthenticationFilter @Autowired constructor(
    private val extractTokenUseCase: ExtractTokenInputBoundary,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val uri = request.requestURI
        if (isStartApiPath(uri)) {
            val token = getJwtFromRequest(request)
            setSecurityContextHolder(token)
        }
        filterChain.doFilter(request, response)
    }

    private fun isStartApiPath(uri: String) = uri.startsWith("/api")

    private fun getJwtFromRequest(request: HttpServletRequest): String {
        val tokenType = "Bearer "
        return runCatching {
            request.getHeader("Authorization").substring(tokenType.length)
        }.getOrNull() ?: throw Unauthorized.TokenNotExistent()
    }

    private fun setSecurityContextHolder(token: String) {
        val userDetail: UserDetails = UserDetail.of(
            extractTokenUseCase(token),
        )
        UsernamePasswordAuthenticationToken(
            userDetail,
            userDetail.password,
            userDetail.authorities,
        ).also { usernamePasswordAuthenticationToken ->
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        }
    }
}
