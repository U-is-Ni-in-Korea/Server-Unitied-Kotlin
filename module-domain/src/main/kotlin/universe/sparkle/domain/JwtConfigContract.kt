package universe.sparkle.domain

import java.time.ZoneId

interface JwtConfigContract {
    fun getSecret(): String
    fun getAccessExpiryPeriodDay(): Long
    fun getRefreshExpiryPeriodDay(): Long

    companion object {
        val zoneIdKST = ZoneId.of("Asia/Seoul")
    }
}
