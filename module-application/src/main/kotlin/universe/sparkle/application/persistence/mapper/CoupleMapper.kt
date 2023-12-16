package universe.sparkle.application.persistence.mapper

import universe.sparkle.domain.model.Couple
import universe.sparkle.application.persistence.NotInitializedIdException
import universe.sparkle.application.persistence.entity.CoupleEntity

fun CoupleEntity.toDomain() = Couple(
    id = this.id ?: throw NotInitializedIdException(),
    startDate = this.startDate,
    heartToken = this.heartToken,
    isDelete = this.isDelete,
)

fun Couple.toEntity() = CoupleEntity(
    id = this.id,
    startDate = this.startDate,
    heartToken = this.heartToken,
    isDelete = this.isDelete,

)
