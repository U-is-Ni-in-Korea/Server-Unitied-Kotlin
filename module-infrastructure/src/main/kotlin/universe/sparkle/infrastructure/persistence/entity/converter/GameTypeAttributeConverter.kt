package universe.sparkle.infrastructure.persistence.entity.converter

import universe.sparkle.domain.GameType

class GameTypeAttributeConverter : EnumAttributeConvertor<GameType>() {
    override fun convertToDataBase(attribute: GameType): String {
        return attribute.name
    }

    override fun convertToEntity(dbData: String): GameType {
        return GameType.valueOf(dbData)
    }
}
