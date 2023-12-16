package universe.sparkle.application.controller.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import universe.sparkle.domain.exception.BusinessException

@Schema(description = "Error Response DTO")
data class ErrorResponseDto(
    @JsonProperty("code") val errorCode: String,
    @JsonProperty("message") val message: String,
)

fun BusinessException.toErrorResponseDto(isDevelopment: Boolean = false) = ErrorResponseDto(
    errorCode = "UE${this.code}",
    message = if (isDevelopment) this.message else this.javaClass.name,
)

fun BusinessException.toHttpStatus() = when (this.code) {
    500 -> HttpStatus.INTERNAL_SERVER_ERROR
    503 -> HttpStatus.SERVICE_UNAVAILABLE
    in 1000 until 2000 -> HttpStatus.BAD_REQUEST
    in 2000 until 3000 -> HttpStatus.UNAUTHORIZED
    in 5000 until 6000 -> HttpStatus.NOT_FOUND
    in 7000 until 8000 -> HttpStatus.NOT_ACCEPTABLE
    in 10000 until 11000 -> HttpStatus.CONFLICT
    in 16000 until 17000 -> HttpStatus.UNSUPPORTED_MEDIA_TYPE
    else -> throw IllegalArgumentException("unsupported error code")
}
