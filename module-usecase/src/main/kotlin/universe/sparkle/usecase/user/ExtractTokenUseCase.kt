package universe.sparkle.usecase.user

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import jakarta.xml.bind.DatatypeConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import universe.sparkle.domain.JwtConfigContract
import universe.sparkle.domain.exception.Unauthorized
import universe.sparkle.domain.model.AuthenticationToken
import universe.sparkle.domain.usecase.BeIssuedAuthTokenInputBoundary
import universe.sparkle.domain.usecase.ExtractTokenInputBoundary
import java.time.LocalDateTime

@Service
class ExtractTokenUseCase @Autowired constructor(
    private val jwtConfig: JwtConfigContract,
) : ExtractTokenInputBoundary {

    override fun invoke(token: String): AuthenticationToken {
        val claims = getClaimsFromToken(token)
        val userId = claims[BeIssuedAuthTokenInputBoundary.CLAIMS_USER_ID]?.toString()?.toLong()
        return AuthenticationToken(
            id = userId ?: throw Unauthorized.UnsupportedToken(),
        )
    }

    private fun getClaimsFromToken(token: String): Claims {
        return runCatching {
            Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtConfig.getSecret()))
                .build()
                .parseClaimsJws(token)
                .body
        }.getOrElse {
            if (it !is JwtException) throw IllegalStateException("Problem occurred during JWT processing")
            if (isExpired(token)) throw Unauthorized.ExpiredToken()
            throw throw Unauthorized.UnsupportedToken()
        }
    }

    private fun isExpired(token: String): Boolean {
        val claims = getClaimsFromToken(token)
        val nowDateTime = LocalDateTime.now(JwtConfigContract.zoneIdKST)
        val tokenExpiredDate = claims.expiration.toInstant().atZone(JwtConfigContract.zoneIdKST).toLocalDateTime()
        return tokenExpiredDate.isBefore(nowDateTime)
    }
}
