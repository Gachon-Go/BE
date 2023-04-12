package gcu.project.gachongo.global.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 공통
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없는 유저입니다."),
    //test
    TEST(HttpStatus.BAD_REQUEST,"에러 테스트"),
    //member
    PASSWORD_ENCRYPTION_ERROR(HttpStatus.CONFLICT, "비밀번호 암호화에 실패하였습니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    USER_FAILED_LOGIN(HttpStatus.NOT_FOUND, "이메일이 없거나 비밀번호가 잘못 되었습니다."),
    REGEX_FAILED_EMAIL(HttpStatus.BAD_REQUEST,"잘못된 이메일 형식입니다.");

    private final HttpStatus status;
    private final String message;

}