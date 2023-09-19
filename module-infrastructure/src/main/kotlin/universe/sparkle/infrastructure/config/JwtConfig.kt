package universe.sparkle.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class JwtConfig(
    @Value("\${jwt.secret}") val secret: String,
    @Value("\${jwt.accessExpiryPeriodDay}") val accessExpiryPeriodDay: Long,
    @Value("\${jwt.refreshExpiryPeriodDay}") val refreshExpiryPeriodDay: Long,
)
