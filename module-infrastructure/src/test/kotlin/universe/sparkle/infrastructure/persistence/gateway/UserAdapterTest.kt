package universe.sparkle.infrastructure.persistence.gateway

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import universe.sparkle.domain.SnsType
import universe.sparkle.domain.exception.BadRequest
import universe.sparkle.domain.gateway.UserGateway
import universe.sparkle.domain.model.User

@DataJpaTest
@AutoConfigureTestDatabase
class UserAdapterTest @Autowired constructor(
    private val userAdapter: UserGateway,
) {
    @Test
    fun 지정된_아이디로_유저를_찾을_수_있다() {
        // given
        유저_아이디_1인_유저를_저장한다()
        // when
        val actualUser = userAdapter.findUserById(1L)
        // then
        assertThat(actualUser.id).isEqualTo(1L)
    }

    private fun 유저_아이디_1인_유저를_저장한다() {
        User(
            id = 1L,
            snsType = SnsType.KAKAO,
            snsAuthCode = "Test",
        ).also { user ->
            userAdapter.saveUser(user)
        }
    }

    @Test
    fun 유저가_존재하지_않는다면_유저가_없다는_에러를_띄워야한다() {
        assertThatThrownBy { userAdapter.findUserById(1L) }
            .isInstanceOf(BadRequest.UserNotExistent::class.java)
            .hasMessage("존재하지 않는 유저의 요청입니다.")
    }
}
