package universe.sparkle.application.filter

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import jakarta.servlet.FilterChain
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import universe.sparkle.domain.exception.Unauthorized
import universe.sparkle.domain.model.AuthenticationToken
import universe.sparkle.domain.usecase.ExtractTokenInputBoundary

@ExtendWith(MockKExtension::class)
class JwtAuthenticationFilterTest {

    @MockK
    private lateinit var extractTokenUseCase: ExtractTokenInputBoundary

    @MockK
    private lateinit var filterChain: FilterChain

    @InjectMockKs
    private lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this.jwtAuthenticationFilter, relaxed = true, relaxUnitFun = true)
    }

    @Test
    fun `JWT 인증이 필요없는 경로에서는 검증을 하지않고 다음 필터를 실행한다`() {
        // given
        val request = MockHttpServletRequest().apply {
            this.requestURI = "/status/uni"
        }
        val response = MockHttpServletResponse()
        every { filterChain.doFilter(request, response) } answers {}
        // when
        jwtAuthenticationFilter.doFilter(
            request,
            response,
            filterChain,
        )
        // then
        assertAll(
            { verify(exactly = 0) { extractTokenUseCase.invoke("") } },
            { verify(exactly = 1) { filterChain.doFilter(request, response) } },
        )
    }

    @Test
    fun `JWT 인증이 필요한 경우 올바른 인증 Header가 없다면 예외가 발생한다`() {
        // given
        val request = MockHttpServletRequest().apply {
            this.requestURI = "/api/testPath"
        }
        val response = MockHttpServletResponse()
        every { filterChain.doFilter(request, response) } answers {}
        every { extractTokenUseCase.invoke("testToken") } answers { `테스트 유저 인증 정보`() }
        // when & then
        assertThatThrownBy {
            jwtAuthenticationFilter.doFilter(request, response, filterChain)
        }.isInstanceOf(Unauthorized.TokenNotExistent::class.java)
            .hasMessage("토큰 값이 존재하지 않습니다.")
    }

    @Test
    fun `JWT 인증이 필요한 경우 인증 후에 다음 필터로 연결한다`() {
        // given
        val testToken = "Test-Token"
        val request = MockHttpServletRequest().apply {
            this.requestURI = "/api/testPath"
            this.addHeader("Authorization", "Bearer $testToken")
        }
        val response = MockHttpServletResponse()
        every { filterChain.doFilter(request, response) } answers {}
        every { extractTokenUseCase.invoke(testToken) } answers { `테스트 유저 인증 정보`() }
        // when
        jwtAuthenticationFilter.doFilter(request, response, filterChain)
        // then
        assertAll(
            { verify(exactly = 1) { extractTokenUseCase.invoke(testToken) } },
            { verify(exactly = 1) { filterChain.doFilter(request, response) } },
        )
    }

    private fun `테스트 유저 인증 정보`() = AuthenticationToken(
        id = 1L,
        nickname = "testUser",
        image = "testImage",
        couple = null,
    )
}
