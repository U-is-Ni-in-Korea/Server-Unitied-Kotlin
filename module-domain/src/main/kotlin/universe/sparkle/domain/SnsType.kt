package universe.sparkle.domain

enum class SnsType {
    KAKAO, GOOGLE, APPLE;

    companion object {
        fun findSnsTypeBy(snsTypeName: String): SnsType {
            return values().find { it.name == snsTypeName }
                ?: throw IllegalArgumentException("Unsupported social login type: $snsTypeName")
        }
    }
}
