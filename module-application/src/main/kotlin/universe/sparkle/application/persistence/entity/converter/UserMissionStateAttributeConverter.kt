package universe.sparkle.application.persistence.entity.converter

import universe.sparkle.domain.UserMissionState

class UserMissionStateAttributeConverter : EnumAttributeConvertor<UserMissionState>() {
    override fun convertToDataBase(attribute: UserMissionState): String {
        return attribute.name
    }

    override fun convertToEntity(dbData: String): UserMissionState {
        return UserMissionState.valueOf(dbData)
    }
}
