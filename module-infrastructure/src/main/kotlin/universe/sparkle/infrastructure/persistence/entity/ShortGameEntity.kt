package universe.sparkle.infrastructure.persistence.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.PrimaryKeyJoinColumn

@Entity
@DiscriminatorValue("ShortGame")
@PrimaryKeyJoinColumn(name = "game_id")
class ShortGameEntity(
    coupleId: Long,
) : GameEntity(
    coupleId = coupleId,
)
