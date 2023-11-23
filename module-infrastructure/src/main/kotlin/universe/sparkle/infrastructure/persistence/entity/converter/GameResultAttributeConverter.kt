package universe.sparkle.infrastructure.persistence.entity.converter

import universe.sparkle.domain.GameResult

class GameResultAttributeConverter : EnumAttributeConvertor<GameResult>() {
    override fun convertToDataBase(attribute: GameResult): String {
        return attribute.name
    }

    override fun convertToEntity(dbData: String): GameResult {
        return GameResult.valueOf(dbData)
    }
}
