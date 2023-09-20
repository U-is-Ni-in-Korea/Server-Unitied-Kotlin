package universe.sparkle.domain.exception

sealed class Conflict(
    override val code: Int,
    override val message: String,
) : BusinessException(code, message)
