package universe.sparkle.application.filter

import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.springframework.util.StreamUtils

class DuplicateAccessibleRequest(
    request: HttpServletRequest,
) : HttpServletRequestWrapper(request) {

    private val cachedInputStream: ByteArray

    init {
        this.cachedInputStream = StreamUtils.copyToByteArray(request.inputStream)
    }

    override fun getInputStream(): ServletInputStream {
        return CachedInputStream(cachedInputStream)
    }
}
