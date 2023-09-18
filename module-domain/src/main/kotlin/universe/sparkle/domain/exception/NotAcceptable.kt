package universe.sparkle.domain.exception

sealed class NotAcceptable(
    override val code: Int,
    override val message: String,
) : BusinessException(code, message)
