package universe.sparkle.application.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "mission_content")
class MissionContentEntity(
    id: Long? = null,
    missionCategoryEntity: MissionCategoryEntity,
    content: String,
    recommendTime: Int? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = id
        protected set

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mission_category_id", nullable = false)
    var missionCategory: MissionCategoryEntity = missionCategoryEntity
        protected set

    @Size(max = 255)
    @NotNull
    @Column(name = "content", nullable = false)
    var content: String = content
        protected set

    @Column(name = "recommend_time")
    var recommendTime: Int? = recommendTime
        protected set
}
