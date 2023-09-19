package universe.sparkle.domain.usecase

interface ValidateTokenInputBoundary {
    operator fun invoke(token: String)
}
