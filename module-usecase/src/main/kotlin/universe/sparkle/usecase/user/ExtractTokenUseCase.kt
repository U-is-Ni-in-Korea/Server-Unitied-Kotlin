package universe.sparkle.usecase.user

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.xml.bind.DatatypeConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import universe.sparkle.domain.JwtConfigContract
import universe.sparkle.domain.exception.Unauthorized
import universe.sparkle.domain.gateway.UserDetailGateway
import universe.sparkle.domain.model.AuthenticationToken
import universe.sparkle.domain.usecase.BeIssuedAuthTokenInputBoundary
import universe.sparkle.domain.usecase.ExtractTokenInputBoundary
import java.time.LocalDateTime

@Service
internal class ExtractTokenUseCase @Autowired constructor(
    private val jwtConfig: JwtConfigContract,
    private val userDetailGateway: UserDetailGateway,
) : ExtractTokenInputBoundary {

    override fun invoke(token: String): AuthenticationToken {
        val claims = getClaimsFromToken(token).also { validateExpired(it) }
        return getUserIdAt(claims)
    }

    override fun getExpiredUserAuthenticationToken(token: String): AuthenticationToken {
        val claims = getClaimsFromToken(token)
        return getUserIdAt(claims)
    }

    private fun getUserIdAt(claims: Claims): AuthenticationToken {
        val userId = claims[BeIssuedAuthTokenInputBoundary.CLAIMS_USER_ID]?.toString()
        return userDetailGateway.loadUserById(userId)
    }

    private fun getClaimsFromToken(token: String): Claims {
        return runCatching {
            Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtConfig.getSecret()))
                .build()
                .parseClaimsJws(token)
                .body
        }.getOrElse {
            when (it) {
                is MalformedJwtException,
                is UnsupportedJwtException,
                is SignatureException,
                -> throw Unauthorized.UnsupportedToken()

                is ExpiredJwtException -> it.claims
                else -> throw IllegalStateException("Problem occurred during JWT processing")
            }
        }
    }

    private fun validateExpired(claims: Claims) {
        val nowDateTime = LocalDateTime.now(JwtConfigContract.zoneIdKST)
        val tokenExpiredDate = claims.expiration.toInstant().atZone(JwtConfigContract.zoneIdKST).toLocalDateTime()
        if (tokenExpiredDate.isBefore(nowDateTime)) throw Unauthorized.ExpiredToken()
    }
}
