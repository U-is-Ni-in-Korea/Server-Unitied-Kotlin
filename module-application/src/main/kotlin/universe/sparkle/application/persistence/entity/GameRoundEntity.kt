package universe.sparkle.application.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import java.time.Instant

@Entity
@Table(name = "game_round")
class GameRoundEntity(
    id: Long? = null,
    gameId: Long,
    missionCategoryId: Long,
    winnerUserId: Long,
    updatedAt: Instant,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = id
        protected set

    @NotNull
    @Column(name = "game_id", nullable = false)
    var gameId: Long = gameId
        protected set

    @Column(name = "mission_category_id")
    var missionCategoryId: Long = missionCategoryId
        protected set

    @Column(name = "winner_user_id")
    var winnerUserId: Long = winnerUserId
        protected set

    @Column(name = "updated_at")
    var updatedAt: Instant? = updatedAt
        protected set
}
