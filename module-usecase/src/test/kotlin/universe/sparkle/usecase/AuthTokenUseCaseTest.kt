package universe.sparkle.usecase

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import universe.sparkle.domain.JwtConfigContract
import universe.sparkle.domain.SnsType
import universe.sparkle.domain.exception.Unauthorized
import universe.sparkle.domain.gateway.UserDetailGateway
import universe.sparkle.domain.gateway.UserGateway
import universe.sparkle.domain.model.AuthenticationToken
import universe.sparkle.domain.model.User
import universe.sparkle.usecase.user.BeIssuedAuthTokenUseCase
import universe.sparkle.usecase.user.ExtractTokenUseCase

@ExtendWith(MockKExtension::class)
class AuthTokenUseCaseTest {

    @MockK
    private lateinit var jwtConfig: JwtConfigContract

    @MockK
    private lateinit var userDetailGateway: UserDetailGateway

    @MockK
    private lateinit var userGateway: UserGateway

    @InjectMockKs
    private lateinit var beIssuedAuthTokenUseCase: BeIssuedAuthTokenUseCase

    @InjectMockKs
    private lateinit var extractTokenUseCase: ExtractTokenUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this.beIssuedAuthTokenUseCase, relaxed = true, relaxUnitFun = true)
        MockKAnnotations.init(this.extractTokenUseCase, relaxed = true, relaxUnitFun = true)
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
        every { userGateway.findUserBySnsAuthCode(user.snsAuthCode) } answers { user }
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
    fun 만료되지_않은_유저의_토큰이_주어진_경우_AuthenticationToken을_통해_userId를_얻을_수_있다() {
        // given
        val user = User(
            id = 1L,
            snsType = SnsType.APPLE,
            snsAuthCode = "test",
        )
        every { jwtConfig.getSecret() } answers { "secretSecretSecretSecretSecretSecretSecretSecretSecret" }
        every { jwtConfig.getAccessExpiryPeriodDay() } answers { 1L }
        every { jwtConfig.getRefreshExpiryPeriodDay() } answers { 2L }
        every { userGateway.findUserBySnsAuthCode(user.snsAuthCode) } answers { user }
        every { userDetailGateway.loadUserById("1") } answers {
            AuthenticationToken(id = 1L, nickname = null, image = null)
        }
        // when
        val authToken = beIssuedAuthTokenUseCase(user)
        // then
        assertAll(
            {
                assertThat(extractTokenUseCase(authToken.accessToken))
                    .isInstanceOf(AuthenticationToken::class.java)
            },
            {
                assertThat(extractTokenUseCase(authToken.accessToken).id)
                    .isEqualTo(1L)
            },
        )
    }

    @Test
    fun 만료된_유저의_토큰이_주어진_경우_토큰_만료_에러가_발생한다() {
        // given
        val user = User(
            id = 1,
            snsType = SnsType.APPLE,
            snsAuthCode = "test",
        )
        every { jwtConfig.getSecret() } answers { "secretSecretSecretSecretSecretSecretSecretSecretSecret" }
        every { jwtConfig.getAccessExpiryPeriodDay() } answers { -1L }
        every { jwtConfig.getRefreshExpiryPeriodDay() } answers { 2L }
        every { userGateway.findUserBySnsAuthCode(user.snsAuthCode) } answers { user }
        every { userDetailGateway.loadUserById("1") } answers {
            AuthenticationToken(id = 1L, nickname = null, image = null)
        }
        // when
        val authToken = beIssuedAuthTokenUseCase(user)
        // then
        assertThatThrownBy { extractTokenUseCase(authToken.accessToken) }
            .isInstanceOf(Unauthorized.ExpiredToken::class.java)
            .hasMessage("토큰이 만료 되었습니다.")
    }

    @Test
    fun 유저의_토큰이_변조된_경우_토큰_변조_에러가_발생한다() {
        // given
        val user = User(
            id = 1,
            snsType = SnsType.APPLE,
            snsAuthCode = "test",
        )
        every { jwtConfig.getSecret() } answers {
            "WiredSecretSecretSecretSecretSecretSecretSecretSecretSecret"
        } andThenAnswer { "SecretSecretSecretSecretSecretSecretSecretSecret" }
        every { jwtConfig.getAccessExpiryPeriodDay() } answers { 1L }
        every { jwtConfig.getRefreshExpiryPeriodDay() } answers { 2L }
        every { userGateway.findUserBySnsAuthCode(user.snsAuthCode) } answers { user }
        every { userDetailGateway.loadUserById("1") } answers {
            AuthenticationToken(id = 1L, nickname = null, image = null)
        }
        // when
        val authToken = beIssuedAuthTokenUseCase(user)
        // then
        assertThatThrownBy { extractTokenUseCase(authToken.accessToken) }
            .isInstanceOf(Unauthorized.UnsupportedToken::class.java)
            .hasMessage("지원하지 않는 방식의 토큰 혹은 변조된 토큰을 사용한 경우입니다.")
    }

    @Test
    fun 토큰에_유저_아이디가_없다면_에러가_발생한다() {
        // given
        val user = User(
            snsType = SnsType.GOOGLE,
            snsAuthCode = "test",
        )
        every { jwtConfig.getSecret() } answers { "secretSecretSecretSecretSecretSecretSecretSecretSecret" }
        every { jwtConfig.getAccessExpiryPeriodDay() } answers { 1L }
        every { jwtConfig.getRefreshExpiryPeriodDay() } answers { 2L }
        every { userGateway.findUserBySnsAuthCode(user.snsAuthCode) } answers { user }
        every { userDetailGateway.loadUserById(null) } answers {
            throw Unauthorized.UnsupportedToken()
        }
        // when
        val authToken = beIssuedAuthTokenUseCase(user)
        // then
        assertThatThrownBy { extractTokenUseCase(authToken.accessToken) }
            .isInstanceOf(Unauthorized.UnsupportedToken::class.java)
            .hasMessage("지원하지 않는 방식의 토큰 혹은 변조된 토큰을 사용한 경우입니다.")
    }
}
