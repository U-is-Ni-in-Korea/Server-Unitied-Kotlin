package universe.sparkle.application.persistence

class NotInitializedIdException(
    override val message: String = "ID has not been initialized yet"
) : IllegalArgumentException(message)
