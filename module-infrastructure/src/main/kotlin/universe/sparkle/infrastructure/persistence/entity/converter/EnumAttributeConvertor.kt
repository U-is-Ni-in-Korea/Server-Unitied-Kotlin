package universe.sparkle.infrastructure.persistence.entity.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
abstract class EnumAttributeConvertor<T : Enum<T>> : AttributeConverter<T, String> {
    override fun convertToDatabaseColumn(attribute: T?): String {
        return attribute?.let {
            convertToDataBase(it)
        } ?: throw IllegalArgumentException("can not convert GameResult to database column")
    }

    override fun convertToEntityAttribute(dbData: String?): T {
        return dbData?.let {
            convertToEntity(it)
        } ?: throw IllegalArgumentException("DB data is not null")
    }

    abstract fun convertToDataBase(attribute: T): String

    abstract fun convertToEntity(dbData: String): T
}
