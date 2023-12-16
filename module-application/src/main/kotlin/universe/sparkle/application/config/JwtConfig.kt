package universe.sparkle.application.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import universe.sparkle.domain.JwtConfigContract

@Configuration
data class JwtConfig(
    @Value("\${jwt.secret}") private val _secret: String,
    @Value("\${jwt.accessExpiryPeriodDay}") private val _accessExpiryPeriodDay: Long,
    @Value("\${jwt.refreshExpiryPeriodDay}") private val _refreshExpiryPeriodDay: Long,
) : JwtConfigContract {

    override fun getSecret() = _secret

    override fun getAccessExpiryPeriodDay() = _accessExpiryPeriodDay

    override fun getRefreshExpiryPeriodDay() = _refreshExpiryPeriodDay
}
