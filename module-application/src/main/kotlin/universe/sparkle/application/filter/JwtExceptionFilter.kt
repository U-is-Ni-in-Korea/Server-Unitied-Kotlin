package universe.sparkle.application.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import universe.sparkle.domain.exception.BadRequest
import universe.sparkle.domain.exception.BusinessException
import universe.sparkle.domain.exception.Unauthorized
import universe.sparkle.application.config.EnvironmentManager
import universe.sparkle.application.controller.response.toErrorResponseDto
import universe.sparkle.application.controller.response.toHttpStatus

@Component
class JwtExceptionFilter @Autowired constructor(
    private val environmentManager: EnvironmentManager,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        runCatching {
            filterChain.doFilter(request, response)
        }.getOrElse {
            when (it) {
                is BadRequest -> setErrorResponse(response, it)
                is Unauthorized -> setErrorResponse(response, it)
                else -> throw it
            }
        }
    }

    private fun setErrorResponse(
        response: HttpServletResponse,
        exception: BusinessException,
    ) {
        val errorResponse = exception.toErrorResponseDto(environmentManager.isDevelopment)
        with(response) {
            characterEncoding = "UTF-8"
            contentType = MediaType.APPLICATION_JSON_VALUE
            status = exception.toHttpStatus().value()
            writer.write(ObjectMapper().writeValueAsString(errorResponse))
        }
    }
}
