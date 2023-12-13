package universe.sparkle.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "notification_information")
class NotificationInformationEntity(
    id: NotificationInformationEntityId,
    enableAllNotification: Boolean = true,
) {
    @EmbeddedId
    var id: NotificationInformationEntityId = id
        protected set

    @Column(name = "enable_all_notification")
    var enableAllNotification: Boolean = enableAllNotification
        protected set
}
