package universe.sparkle.application.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import universe.sparkle.domain.GameResult
import universe.sparkle.application.persistence.entity.converter.GameResultAttributeConverter
import java.time.Instant

@Entity
@Table(name = "game")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "GAME_TYPE")
class GameEntity(
    id: Long? = null,
    finishedAt: Instant? = null,
    result: GameResult = GameResult.UNDECIDED,
    coupleId: Long,
    onDelete: Boolean = false,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = id
        protected set

    @Column(name = "finished_at")
    var finishedAt: Instant? = finishedAt
        protected set

    @Size(max = 10)
    @Convert(converter = GameResultAttributeConverter::class)
    @Column(name = "result", length = 10)
    var result: GameResult = result
        protected set

    @NotNull
    @Column(name = "couple_id", nullable = false)
    var couple: Long = coupleId
        protected set

    @NotNull
    @Column(name = "on_delete", nullable = false)
    var onDelete: Boolean = onDelete
        protected set
}
