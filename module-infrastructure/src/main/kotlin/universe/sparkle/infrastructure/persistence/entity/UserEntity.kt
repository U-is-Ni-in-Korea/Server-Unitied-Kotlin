package universe.sparkle.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import universe.sparkle.domain.SnsType
import universe.sparkle.domain.model.User
import universe.sparkle.infrastructure.persistence.entity.converter.SnsTypeAttributeConverter

@Entity
@Table(name = "user")
class UserEntity(
    id: Long? = null,
    snsType: SnsType,
    snsAuthCode: String,
    nickname: String? = null,
    image: String? = null,
    couple: CoupleEntity? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    var couple: CoupleEntity? = couple
        protected set

    fun update(user: User) {
        if (user.id != this.id) throw IllegalStateException("서로 다른 유저 정보로 업데이트가 불가합니다.")
        nickname = user.nickname
        image = user.image
    }
}
