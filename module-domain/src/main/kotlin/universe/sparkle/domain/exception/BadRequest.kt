package universe.sparkle.domain.exception

sealed class BadRequest(
    code: Int,
    message: String,
) : BusinessException(code, message) {
    data class InvalidRequestMethod(
        override val message: String = "잘못된 방식의 요청입니다.",
    ) : BadRequest(1001, message)

    data class AlreadyGameCreated(
        override val message: String = "이미 승부가 생성되었습니다.",
    ) : BadRequest(1002, message)

    data class UserNotExistent(
        override val message: String = "존재하지 않는 유저의 요청입니다.",
    ) : BadRequest(1003, message)

    data class AlreadyGameDone(
        override val message: String = "이미 게임이 종료 되었습니다.",
    ) : BadRequest(1004, message)

    data class CoupleNotExistent(
        override val message: String = "존재하지 않는 커플의 요청입니다.",
    ) : BadRequest(1005, message)

    data class InvalidInviteCode(
        override val message: String = "올바르지 않은 초대 코드입니다.",
    ) : BadRequest(1006, message)

    data class PartnerResultNotEntered(
        override val message: String = "상대방이 아직 결과를 입력하지 않았습니다.",
    ) : BadRequest(1007, message)
}
