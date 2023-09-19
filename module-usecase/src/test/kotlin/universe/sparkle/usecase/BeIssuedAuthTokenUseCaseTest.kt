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
import org.junit.jupiter.api.extension.ExtendWith
import universe.sparkle.domain.JwtConfigContract
import universe.sparkle.domain.SnsType
import universe.sparkle.domain.model.User
import universe.sparkle.usecase.user.BeIssuedAuthTokenUseCase

@ExtendWith(MockKExtension::class)
class AuthTokenUseCaseTest {

    @MockK
    private lateinit var jwtConfig: JwtConfigContract

    @InjectMockKs
    private lateinit var beIssuedAuthTokenUseCase: BeIssuedAuthTokenUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        every { jwtConfig.getSecret() } answers { "secretSecretSecretSecretSecretSecretSecretSecretSecret" }
        every { jwtConfig.getAccessExpiryPeriodDay() } answers { 1L }
        every { jwtConfig.getRefreshExpiryPeriodDay() } answers { 2L }
    }

    @Test
    fun 유저_정보가_주어지면_유저_정보를_통해_인증_토큰을_생성한다() {
        // given
        val user = User(
            id = 1,
            snsType = SnsType.APPLE,
            snsAuthCode = "test",
        )
        // when
        val authToken = beIssuedAuthTokenUseCase(user)
        // then
        assertAll(
            { assertThat(authToken.accessToken).isNotNull() },
            { assertThat(authToken.refreshToken).isNull() },
            { assertThat(authToken.coupleId).isNull() },
        )
    }
}
