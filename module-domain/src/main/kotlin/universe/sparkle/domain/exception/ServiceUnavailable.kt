package universe.sparkle.domain.exception

sealed class ServiceUnavailable(
    override val code: Int,
    override val message: String,
) : BusinessException(code, message)
