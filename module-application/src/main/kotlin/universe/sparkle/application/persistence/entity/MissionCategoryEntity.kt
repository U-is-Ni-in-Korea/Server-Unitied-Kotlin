package universe.sparkle.application.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import universe.sparkle.domain.MissionTool
import universe.sparkle.domain.MissionType
import universe.sparkle.application.persistence.entity.converter.MissionToolAttributeConverter
import universe.sparkle.application.persistence.entity.converter.MissionTypeAttributeConverter

@Entity
@Table(name = "mission_category")
class MissionCategoryEntity(
    id: Long? = null,
    title: String,
    description: String,
    rule: String,
    tip: String,
    example: String,
    image: String,
    level: Int,
    expectedTime: Int? = null,
    missionType: MissionType,
    missionTool: MissionTool,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = id
        protected set

    @Size(max = 50)
    @NotNull
    @Column(name = "title", nullable = false, length = 50)
    var title: String = title
        protected set

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    var description: String = description
        protected set

    @Size(max = 255)
    @NotNull
    @Column(name = "rule", nullable = false)
    var rule: String = rule
        protected set

    @Size(max = 255)
    @NotNull
    @Column(name = "tip", nullable = false)
    var tip: String = tip
        protected set

    @Size(max = 255)
    @NotNull
    @Column(name = "example", nullable = false)
    var example: String = example
        protected set

    @Size(max = 255)
    @NotNull
    @Column(name = "image", nullable = false)
    var image: String = image
        protected set

    @NotNull
    @Column(name = "level", nullable = false)
    var level: Int = level
        protected set

    @Column(name = "expected_time", nullable = false)
    var expectedTime: Int? = expectedTime
        protected set

    @Size(max = 30)
    @NotNull
    @Convert(converter = MissionTypeAttributeConverter::class)
    @Column(name = "mission_type", nullable = false, length = 30)
    var missionType: MissionType = missionType
        protected set

    @Size(max = 30)
    @NotNull
    @Convert(converter = MissionToolAttributeConverter::class)
    @Column(name = "mission_tool", nullable = false, length = 30)
    var missionTool: MissionTool = missionTool
        protected set
}
