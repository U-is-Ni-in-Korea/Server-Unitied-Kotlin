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
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import universe.sparkle.domain.GameType
import universe.sparkle.infrastructure.persistence.entity.converter.GameTypeAttributeConverter
import java.time.Instant

@Entity
@Table(name = "wish_coupon")
class WishCouponEntity(
    id: Long? = null,
    content: String? = null,
    visible: Boolean = false,
    usable: Boolean = false,
    usedAt: Instant? = null,
    ownerUserEntity: UserEntity,
    gameEntity: GameEntity? = null,
    gameType: GameType,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = id
        protected set

    @Size(max = 180)
    @Column(name = "content", length = 180)
    var content: String? = content
        protected set

    @NotNull
    @Column(name = "visible", nullable = false)
    var visible: Boolean = visible
        protected set

    @NotNull
    @Column(name = "usable", nullable = false)
    var usable: Boolean = usable
        protected set

    @Column(name = "used_at")
    var usedAt: Instant? = usedAt
        protected set

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "owner_user_id", nullable = false)
    var ownerUserEntity: UserEntity = ownerUserEntity
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "game_id")
    var gameEntity: GameEntity? = gameEntity
        protected set

    @Size(max = 20)
    @NotNull
    @Convert(converter = GameTypeAttributeConverter::class)
    @Column(name = "game_type", nullable = false, length = 20)
    var gameType: GameType? = gameType
        protected set
}
