package universe.sparkle.domain.model

import universe.sparkle.domain.SnsType

data class User(
    val id: Long,
    val snsType: SnsType,
    val snsAuthCode: String,
    val nickname: String?,
    val image: String?,
    val fcmToken: String?,
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
