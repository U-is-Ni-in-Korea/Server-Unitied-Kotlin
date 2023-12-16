package universe.sparkle.application.persistence.entity.converter

import universe.sparkle.domain.MissionTool

class MissionToolAttributeConverter : EnumAttributeConvertor<MissionTool>() {
    override fun convertToDataBase(attribute: MissionTool): String {
        return attribute.name
    }

    override fun convertToEntity(dbData: String): MissionTool {
        return MissionTool.valueOf(dbData)
    }
}
