package universe.sparkle.infrastructure.persistence.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import universe.sparkle.domain.SnsType
import universe.sparkle.infrastructure.persistence.entity.UserEntity
import universe.sparkle.infrastructure.persistence.repository.UserJpaRepository

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserJpaRepositoryTest @Autowired constructor(
    private val userJpaRepository: UserJpaRepository,
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
        val actualUser = userJpaRepository.save(expectedUser)
        // then
        assertAll(
            { assertThat(actualUser.id).isNotNull() },
            { assertThat(actualUser.snsType).isEqualTo(expectedSnsType) },
            { assertThat(actualUser.snsAuthCode).isEqualTo(expectedAuthCode) },
        )
    }
}
