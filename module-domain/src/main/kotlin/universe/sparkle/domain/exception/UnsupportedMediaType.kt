package universe.sparkle.domain.exception

sealed class UnsupportedMediaType(
    override val code: Int,
    override val message: String,
) : BusinessException(code, message)
