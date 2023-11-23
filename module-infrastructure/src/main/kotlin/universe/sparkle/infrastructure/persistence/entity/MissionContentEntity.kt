package universe.sparkle.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "mission_content")
class MissionContentEntity(
    id: Long? = null,
    missionCategoryId: Long,
    content: String,
    recommendTime: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mission_content_id")
    var id: Long? = id
        protected set

    @Column(name = "mission_category_id", nullable = false)
    var missionCategoryId: Long = missionCategoryId
        protected set

    @Column(name = "content", nullable = false)
    var content: String = content
        protected set

    @Column(name = "recommend_time", nullable = false)
    var recommendTime: String = recommendTime
        protected set
}
