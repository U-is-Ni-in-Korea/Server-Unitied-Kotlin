package universe.sparkle.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import universe.sparkle.domain.MissionTool
import universe.sparkle.domain.MissionType
import universe.sparkle.infrastructure.persistence.entity.converter.MissionToolAttributeConverter
import universe.sparkle.infrastructure.persistence.entity.converter.MissionTypeAttributeConverter

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
    expectedTime: Int,
    missionType: MissionType,
    missionTool: MissionTool,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mission_category_id")
    var id: Long? = id
        protected set

    @Column(name = "title", nullable = false)
    var title: String = title
        protected set

    @Column(name = "description", nullable = false)
    var description: String = description
        protected set

    @Column(name = "rule", nullable = false)
    var rule: String = rule
        protected set

    @Column(name = "tip", nullable = false)
    var tip: String = tip
        protected set

    @Column(name = "example", nullable = false)
    var example: String = example
        protected set

    @Column(name = "image", nullable = false)
    var image: String = image
        protected set

    @Column(name = "level", nullable = false)
    var level: Int = level
        protected set

    @Column(name = "expected_time", nullable = false)
    var expectedTime: Int = expectedTime
        protected set

    @Column(name = "mission_type", nullable = false)
    @Convert(converter = MissionTypeAttributeConverter::class)
    var missionType: MissionType = missionType
        protected set

    @Column(name = "mission_tool", nullable = false)
    @Convert(converter = MissionToolAttributeConverter::class)
    var missionTool: MissionTool = missionTool
        protected set
}
