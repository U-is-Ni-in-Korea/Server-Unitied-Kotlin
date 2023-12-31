package universe.sparkle.application.persistence.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import universe.sparkle.domain.SnsType
import universe.sparkle.application.persistence.entity.UserEntity

@DataJpaTest
@AutoConfigureTestDatabase
class UserRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository,
) {

    @ParameterizedTest
    @CsvSource("KAKAO,kakaoTestAuthCode", "GOOGLE, googleTestAuthCode", "APPLE, appleTestAuthCode")
    fun 유저의_소설_로그인_정보가_주어지면_유저를_생성하여_유저를_저장할_수_있다(
        expectedSnsType: SnsType,
        expectedAuthCode: String,
    ) {
        // given
        val expectedUser = UserEntity(
            snsType = expectedSnsType,
            snsAuthCode = expectedAuthCode,
        )
        // when
        val actualUser = userRepository.save(expectedUser)
        // then
        assertAll(
            { assertThat(actualUser.id).isNotNull() },
            { assertThat(actualUser.snsType).isEqualTo(expectedSnsType) },
            { assertThat(actualUser.snsAuthCode).isEqualTo(expectedAuthCode) },
        )
    }
}
