package universe.sparkle.application.persistence.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import universe.sparkle.domain.SnsType
import universe.sparkle.domain.model.User
import universe.sparkle.application.persistence.entity.UserEntity

class UserMapperTest {
    @Test
    fun 유저_엔티티를_도메인_모델로_변경할_수_있다() {
        // given
        val expectedUserEntity = UserEntity(
            id = 1L,
            snsType = SnsType.APPLE,
            snsAuthCode = "testAuthCode",
        )
        // when
        val user = expectedUserEntity.toDomain()
        // then
        assertThat(user).isInstanceOf(User::class.java)
    }

    @Test
    fun 유저_도메인_모델을_엔티티로_변경할_수_있다() {
        // given
        val expectedUser = User(
            snsType = SnsType.GOOGLE,
            snsAuthCode = "test",
        )
        // when
        val userEntity = expectedUser.toEntity()
        // then
        assertThat(userEntity).isInstanceOf(UserEntity::class.java)
    }

    @Test
    fun 유저_엔티티에서_아이디가_지정되지_않으면_도메인_모델로_변경_할_수_없다() {
        // given
        val expectedUserEntity = UserEntity(
            snsType = SnsType.APPLE,
            snsAuthCode = "testAuthCode",
        )
        // when
        // then
        assertThatThrownBy { expectedUserEntity.toDomain() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("ID has not been initialized yet")
    }
}
