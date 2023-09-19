package universe.sparkle.domain

interface JwtConfigContract {
    fun getSecret(): String
    fun getAccessExpiryPeriodDay(): Long
    fun getRefreshExpiryPeriodDay(): Long
}
