package universe.sparkle.domain.model

import universe.sparkle.domain.SnsType

data class User(
    val id: Long? = null,
    val snsType: SnsType,
    val snsAuthCode: String,
    val nickname: String? = null,
    val image: String? = null,
    val fcmToken: String? = null,
) {

    init {
        validNickname(nickname)
    }

    fun updateProfile(updateNickname: String?, updateImage: String?): User {
        validNickname(updateNickname)
        return copy(
            id = this.id,
            snsType = this.snsType,
            snsAuthCode = this.snsAuthCode,
            nickname = updateNickname,
            image = updateImage,
            fcmToken = this.fcmToken,
        )
    }

    private fun validNickname(value: String?) {
        require(value?.let { it.length > 10 } ?: true)
    }
}

fun User.toAuthenticationToken() = this.id?.let { userId ->
    AuthenticationToken(
        id = userId,
        nickname = this.nickname,
        image = this.image,
    )
}
