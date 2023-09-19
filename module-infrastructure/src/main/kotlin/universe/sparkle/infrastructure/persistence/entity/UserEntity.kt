package universe.sparkle.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import universe.sparkle.domain.SnsType
import universe.sparkle.infrastructure.persistence.entity.converter.SnsTypeAttributeConverter

@Entity
@Table(name = "user")
class UserEntity(
    id: Long? = null,
    snsType: SnsType,
    snsAuthCode: String,
    nickname: String? = null,
    image: String? = null,
    fcmToken: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    var id: Long? = id
        protected set

    @Convert(converter = SnsTypeAttributeConverter::class)
    @Column(name = "sns_type", nullable = false)
    var snsType: SnsType = snsType
        protected set

    @Column(name = "sns_auth", nullable = false)
    var snsAuthCode: String = snsAuthCode
        protected set

    @Column(name = "nickname", length = 10)
    var nickname: String? = nickname
        protected set

    @Column(name = "image")
    var image: String? = image
        protected set

    @Column(name = "fcm_token")
    var fcmToken: String? = fcmToken
        protected set
}
