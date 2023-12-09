package universe.sparkle.domain.model

import java.time.LocalDate

data class Couple(
    val id: Long? = null,
    val startDate: LocalDate,
    val heartToken: Int = 5,
    val isDelete: Boolean = false,
) {
}
