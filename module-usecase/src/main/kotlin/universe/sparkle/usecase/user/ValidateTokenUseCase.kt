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
import universe.sparkle.domain.usecase.ValidateTokenInputBoundary
import java.time.LocalDateTime

@Service
class ValidateTokenUseCase @Autowired constructor(
    private val jwtConfig: JwtConfigContract,
) : ValidateTokenInputBoundary {

    override fun invoke(token: String) {
        if (isExpired(token)) throw Unauthorized.ExpiredToken()
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
                is ExpiredJwtException -> throw Unauthorized.ExpiredToken()
                is MalformedJwtException,
                is UnsupportedJwtException,
                is SignatureException,
                -> throw Unauthorized.UnsupportedToken()

                else -> throw IllegalStateException("Problem occurred during JWT processing")
            }
        }
    }

    private fun isExpired(token: String): Boolean {
        val claims = getClaimsFromToken(token)
        val nowDateTime = LocalDateTime.now(JwtConfigContract.zoneIdKST)
        val tokenExpiredDate = claims.expiration.toInstant().atZone(JwtConfigContract.zoneIdKST).toLocalDateTime()
        return tokenExpiredDate.isBefore(nowDateTime)
    }
}
