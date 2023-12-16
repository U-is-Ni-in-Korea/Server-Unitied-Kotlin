package universe.sparkle.application.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import java.io.InputStream

@Component
class RequestAndResponseLogFilter : OncePerRequestFilter() {
    val requestLogger by Logging()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response)
        } else {
            val duplicateAccessibleRequest = DuplicateAccessibleRequest(request)
            val cachingResponseWrapper = ContentCachingResponseWrapper(response)
            loggingDoFilter(duplicateAccessibleRequest, cachingResponseWrapper, filterChain)
            cachingResponseWrapper.copyBodyToResponse()
        }
    }

    private fun loggingDoFilter(
        request: DuplicateAccessibleRequest,
        response: ContentCachingResponseWrapper,
        filterChain: FilterChain,
    ) = try {
        logRequest(request)
        logPayload(request.contentType ?: "application/json", request.inputStream)
        filterChain.doFilter(request, response)
    } finally {
        logResponse(response)
        logPayload(response.contentType ?: "application/json", response.contentInputStream)
    }

    @Throws(IOException::class)
    private fun logRequest(request: DuplicateAccessibleRequest) = requestLogger.run {
        info("<< Request   : [${request.method}] ${request.requestURI}${request.queryString ?: ""}")
        info("content-Type : ${request.contentType ?: "Unknown"}")
        info("IP           : ${request.remoteAddr}")
    }

    @Throws(IOException::class)
    private fun logResponse(response: ContentCachingResponseWrapper) = requestLogger.run {
        info(">> Response  : ${HttpStatus.valueOf(response.status)}")
        info("Content-Type : ${response.contentType ?: "Unknown"}")
    }

    private fun logPayload(contentType: String, inputStream: InputStream) = requestLogger.run {
        if (isVisible(MediaType.valueOf(contentType))) {
            val content = StreamUtils.copyToByteArray(inputStream)
            info("Body         : ${if (content.isNotEmpty()) String(content) else ""}")
        } else {
            info("Body         : Binary Content")
        }
    }

    private fun isVisible(mediaType: MediaType): Boolean {
        return listOf<MediaType>(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA,
        ).stream().anyMatch { it.includes(mediaType) }
    }
}
