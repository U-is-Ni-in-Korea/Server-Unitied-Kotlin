package universe.sparkle.application.persistence.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table
import universe.sparkle.domain.GameType

@Entity
@Table(name = "short_game")
@DiscriminatorValue(value = GameType.CONTRACT_SHORT)
class ShortGameEntity(
    coupleId: Long,
) : GameEntity(coupleId = coupleId)
