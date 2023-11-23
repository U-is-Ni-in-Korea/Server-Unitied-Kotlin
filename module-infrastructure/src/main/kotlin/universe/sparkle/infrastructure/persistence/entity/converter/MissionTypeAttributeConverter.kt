package universe.sparkle.infrastructure.persistence.entity.converter

import universe.sparkle.domain.MissionType

class MissionTypeAttributeConverter : EnumAttributeConvertor<MissionType>() {
    override fun convertToDataBase(attribute: MissionType): String {
        return attribute.name
    }

    override fun convertToEntity(dbData: String): MissionType {
        return MissionType.valueOf(dbData)
    }
}
