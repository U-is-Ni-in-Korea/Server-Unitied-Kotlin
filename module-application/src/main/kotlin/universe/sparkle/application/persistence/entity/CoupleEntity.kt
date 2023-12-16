package universe.sparkle.application.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDate

@Entity
@Table(name = "couple")
class CoupleEntity(
    id: Long? = null,
    startDate: LocalDate,
    heartToken: Int = 5,
    isDelete: Boolean = false,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long? = id
        protected set

    @Column(name = "start_date", nullable = false)
    var startDate: LocalDate = startDate
        protected set

    @Column(name = "heart_token", nullable = false)
    @ColumnDefault("5")
    var heartToken: Int = heartToken
        protected set

    @Column(name = "is_delete", nullable = false)
    @ColumnDefault("false")
    var isDelete: Boolean = isDelete
}
