package universe.sparkle.application.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.Objects

@Embeddable
class NotificationInformationEntityId(
    userId: Long,
    fcmToken: String,
) : Serializable {
    @NotNull
    @Column(name = "user_id", nullable = false)
    var userId: Long = userId
        protected set

    @Size(max = 255)
    @NotNull
    @Column(name = "fcm_token", nullable = false)
    var fcmToken: String = fcmToken
        protected set

    override fun hashCode(): Int = Objects.hash(userId, fcmToken)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as NotificationInformationEntityId

        return userId == other.userId &&
            fcmToken == other.fcmToken
    }

    companion object {
        private const val serialVersionUID = -7994741440230625710L
    }
}
