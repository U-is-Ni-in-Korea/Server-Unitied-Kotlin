package universe.sparkle.infrastructure.persistence.entity.converter

import universe.sparkle.domain.SnsType

class SnsTypeAttributeConverter : EnumAttributeConvertor<SnsType>() {

    override fun convertToEntity(dbData: String): SnsType {
        return SnsType.valueOf(dbData)
    }

    override fun convertToDataBase(attribute: SnsType): String {
        return attribute.name
    }
}
