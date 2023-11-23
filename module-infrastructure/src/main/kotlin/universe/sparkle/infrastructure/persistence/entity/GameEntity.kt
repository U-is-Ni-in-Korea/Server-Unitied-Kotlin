package universe.sparkle.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "game")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
open class GameEntity(
    id: Long? = null,
    coupleId: Long,
    enable: Boolean = true,
    finishedAt: LocalDateTime? = null,
) {
    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
        protected set

    @Column(name = "couple_id", nullable = false)
    var coupleId = coupleId
        protected set

    @Column(name = "enable", nullable = false)
    var enable: Boolean = enable
        protected set

    @Column(name = "finished_at")
    var finishedAt: LocalDateTime? = finishedAt
        protected set
}
