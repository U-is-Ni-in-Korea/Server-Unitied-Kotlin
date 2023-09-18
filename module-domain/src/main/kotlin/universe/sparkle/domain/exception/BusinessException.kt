package universe.sparkle.domain.exception

sealed class BusinessException(
    open val code: Int,
    override val message: String,
) : RuntimeException(message) {

    // 404 Not Found
    sealed class NotFound(
        override val code: Int,
        override val message: String,
    ) : BusinessException(code, message)

    // 406 Not Acceptable
    sealed class NotAcceptable(
        override val code: Int,
        override val message: String,
    ) : BusinessException(code, message)

    // 409 Conflict
    sealed class Conflict(
        override val code: Int,
        override val message: String,
    ) : BusinessException(code, message)

    // 415 Unsupported Media Type
    sealed class UnsupportedMediaType(
        override val code: Int,
        override val message: String,
    ) : BusinessException(code, message)

    // 500 Internal Server Error
    sealed class InternalServerError(
        override val code: Int,
        override val message: String,
    ) : BusinessException(code, message)

    // 503 Service Unavailable
    sealed class ServiceUnavailable(
        override val code: Int,
        override val message: String,
    ) : BusinessException(code, message)
}
