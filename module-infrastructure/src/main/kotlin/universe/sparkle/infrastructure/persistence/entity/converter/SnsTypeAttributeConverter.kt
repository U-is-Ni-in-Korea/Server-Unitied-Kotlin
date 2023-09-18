package universe.sparkle.infrastructure.persistence.entity.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import universe.sparkle.domain.SnsType

@Converter
class SnsTypeAttributeConverter : AttributeConverter<SnsType, String> {

    override fun convertToDatabaseColumn(attribute: SnsType?): String {
        return attribute?.name ?: throw IllegalArgumentException("can not convert SnsType to database column")
    }

    override fun convertToEntityAttribute(dbData: String?): SnsType {
        return dbData?.let { SnsType.findSnsTypeBy(it) }
            ?: throw IllegalArgumentException("DB data is not null")
    }
}
