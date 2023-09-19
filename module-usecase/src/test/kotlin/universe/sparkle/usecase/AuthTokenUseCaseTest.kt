package universe.sparkle.usecase

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import universe.sparkle.domain.JwtConfigContract
import universe.sparkle.domain.SnsType
import universe.sparkle.domain.model.User
import universe.sparkle.usecase.user.BeIssuedAuthTokenUseCase
import universe.sparkle.usecase.user.ValidateTokenUseCase

@ExtendWith(MockKExtension::class)
class AuthTokenUseCaseTest {

    @MockK
    private lateinit var jwtConfig: JwtConfigContract

    @InjectMockKs
    private lateinit var beIssuedAuthTokenUseCase: BeIssuedAuthTokenUseCase

    @InjectMockKs
    private lateinit var validateTokenUseCase: ValidateTokenUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this.beIssuedAuthTokenUseCase, relaxed = true, relaxUnitFun = true)
        MockKAnnotations.init(this.validateTokenUseCase, relaxed = true, relaxUnitFun = true)
    }

    @Test
    fun 유저_정보가_주어지면_유저_정보를_통해_인증_토큰을_생성한다() {
        // given
        val user = User(
            id = 1,
            snsType = SnsType.APPLE,
            snsAuthCode = "test",
        )
        every { jwtConfig.getSecret() } answers { "secretSecretSecretSecretSecretSecretSecretSecretSecret" }
        every { jwtConfig.getAccessExpiryPeriodDay() } answers { 1L }
        every { jwtConfig.getRefreshExpiryPeriodDay() } answers { 2L }
        // when
        val authToken = beIssuedAuthTokenUseCase(user)
        // then
        assertAll(
            { assertThat(authToken.accessToken).isNotNull() },
            { assertThat(authToken.refreshToken).isNull() },
            { assertThat(authToken.coupleId).isNull() },
        )
    }

    @Test
    fun 만료되지_않은_유저의_토큰이_주어진_경우_검증을_통과한다() {
        // given
        val user = User(
            id = 1,
            snsType = SnsType.APPLE,
            snsAuthCode = "test",
        )
        every { jwtConfig.getSecret() } answers { "secretSecretSecretSecretSecretSecretSecretSecretSecret" }
        every { jwtConfig.getAccessExpiryPeriodDay() } answers { 1L }
        every { jwtConfig.getRefreshExpiryPeriodDay() } answers { 2L }
        // when
        val authToken = beIssuedAuthTokenUseCase(user)
        // then
        assertDoesNotThrow {
            validateTokenUseCase(authToken.accessToken)
        }
    }

    // TODO 토큰이 만료된 경우 테스트 작성

    // TODO 토큰이 변조된 경우 테스트 작성
}
