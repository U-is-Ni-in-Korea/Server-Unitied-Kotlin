package universe.sparkle.usecase.user

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.xml.bind.DatatypeConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import universe.sparkle.domain.usecase.BeIssuedAuthTokenInputBoundary
import universe.sparkle.domain.JwtConfigContract
import universe.sparkle.domain.gateway.UserGateway
import universe.sparkle.domain.model.AuthToken
import universe.sparkle.domain.model.User
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Service
internal class BeIssuedAuthTokenUseCase @Autowired constructor(
    private val jwtConfig: JwtConfigContract,
    private val userGateway: UserGateway,
) : BeIssuedAuthTokenInputBoundary {

    private val signatureAlgorithm = SignatureAlgorithm.HS256

    override fun invoke(
        user: User,
        accessExpiryPeriodDay: Long,
        refreshExpiryPeriodDay: Long,
    ): AuthToken {
        val secretKeyBytes = DatatypeConverter.parseBase64Binary(jwtConfig.getSecret())
        val signingKey = SecretKeySpec(secretKeyBytes, signatureAlgorithm.jcaName)
        val savedUser = userGateway.findUserBySnsAuthCode(user.snsAuthCode) ?: userGateway.saveUser(user)
        return AuthToken(
            accessToken = beIssuedAccessToken(savedUser, accessExpiryPeriodDay, signingKey),
        )
    }

    override fun invoke(user: User): AuthToken {
        return this.invoke(
            user = user,
            accessExpiryPeriodDay = jwtConfig.getAccessExpiryPeriodDay(),
            refreshExpiryPeriodDay = jwtConfig.getRefreshExpiryPeriodDay(),
        )
    }

    private fun beIssuedAccessToken(
        user: User,
        accessExpiryPeriodDay: Long,
        signingKey: SecretKeySpec,
    ): String = createExpiration(accessExpiryPeriodDay).let { expirationDay ->
        Jwts.builder()
            .setClaims(createAccessTokenClaims(user))
            .setExpiration(Date.from(expirationDay))
            .signWith(signingKey, signatureAlgorithm)
            .compact()
    }

    private fun createAccessTokenClaims(user: User) = Jwts.claims()
        .setSubject(BeIssuedAuthTokenInputBoundary.ACCESS_TOKEN_SUBJECT)
        .also {
            it[BeIssuedAuthTokenInputBoundary.CLAIMS_USER_ID] = user.id
        }

    private fun createExpiration(expiryPeriod: Long): Instant = LocalDateTime.now()
        .atZone(JwtConfigContract.zoneIdKST)
        .toLocalDateTime()
        .plusDays(expiryPeriod)
        .atZone(JwtConfigContract.zoneIdKST)
        .toInstant()
}
