package universe.sparkle.application.filter

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import java.io.ByteArrayInputStream
import java.io.IOException

class CachedInputStream(
    cachedInputStream: ByteArray,
) : ServletInputStream() {

    private val cachedBodyInputStream: ByteArrayInputStream

    init {
        this.cachedBodyInputStream = ByteArrayInputStream(cachedInputStream)
    }

    override fun read(): Int = cachedBodyInputStream.read()

    override fun isFinished(): Boolean {
        try {
            return cachedBodyInputStream.available() == 0
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return false
    }

    override fun isReady(): Boolean = true

    override fun setReadListener(listener: ReadListener?) {
        throw UnsupportedOperationException("Unsupported Operation setReadListener in CachedInputStream Object")
    }
}
