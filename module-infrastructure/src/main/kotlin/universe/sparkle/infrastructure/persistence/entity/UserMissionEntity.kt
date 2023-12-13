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
import universe.sparkle.domain.UserMissionState
import universe.sparkle.infrastructure.persistence.entity.converter.UserMissionStateAttributeConverter
import java.time.Instant

@Entity
@Table(name = "user_mission")
class UserMissionEntity(
    id: Long? = null,
    roundEntity: GameRoundEntity,
    missionContentEntity: MissionContentEntity,
    userId: Long,
    result: UserMissionState = UserMissionState.UNDECIDED,
    updatedAt: Instant? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = id
        protected set

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "round_id", nullable = false)
    var roundEntity: GameRoundEntity = roundEntity
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "mission_content_id")
    var missionContentEntity: MissionContentEntity = missionContentEntity
        protected set

    @NotNull
    @Column(name = "user_id", nullable = false)
    var userId: Long = userId
        protected set

    @Size(max = 10)
    @Convert(converter = UserMissionStateAttributeConverter::class)
    @Column(name = "result", length = 10)
    var result: UserMissionState = result
        protected set

    @Column(name = "updated_at")
    var updatedAt: Instant? = updatedAt
        protected set
}
