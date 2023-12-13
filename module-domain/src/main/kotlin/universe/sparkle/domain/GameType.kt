package universe.sparkle.domain

enum class GameType {
    SHORT, LONG;

    companion object {
        const val CONTRACT_SHORT = "SHORT"
        const val CONTRACT_LONG = "LONG"
    }
}
