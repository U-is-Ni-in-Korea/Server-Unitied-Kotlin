package universe.sparkle.domain.exception

sealed class BusinessException(
    open val code: Int,
    override val message: String,
) : RuntimeException(message)
