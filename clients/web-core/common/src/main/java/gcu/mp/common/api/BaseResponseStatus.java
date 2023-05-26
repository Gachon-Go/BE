package gcu.mp.common.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, HttpStatus.OK, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, HttpStatus.OK, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, HttpStatus.UNAUTHORIZED, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false, 2003, HttpStatus.UNAUTHORIZED, "권한이 없는 유저의 접근입니다."),
    INVALID_TOKEN(false, 2004, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    METHOD_NOT_ALLOWED(false, 2005, HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드입니다."),

    REGEX_FAILED_EMAIL(false, 2010, HttpStatus.OK, "유효한 이메일 정규식이 아닙니다."),
    MAIL_CERTIFICATION_FAIL(false, 2011, HttpStatus.OK, "이메일 인증에 실패하였습니다."),
    UNAUTHORIZED(false, 2012, HttpStatus.UNAUTHORIZED, "권한이 없는 유저의 접근입니다."),

    // 2100 users
    EXISTS_MEMBER(false, 2100, HttpStatus.OK, "이미 존재하는 유저입니다."),
    EXISTS_MEMBER_NICKNAME(false, 2101, HttpStatus.OK, "사용중인 유저 닉네임 입니다."),
    EXISTS_EMAIL(false, 2102, HttpStatus.OK, "사용중인 이메일입니다."),
    NOT_EXIST_MEMBER(false, 2103, HttpStatus.OK, "존재하지 않는 유저입니다."),
    // 2200 post
    NOT_EXIST_POST(false, 2203, HttpStatus.OK, "존재하지 않는 게시물입니다."),
    // 2300
    INVALID_PURPOSE_VALUE(false, 2310, HttpStatus.OK, "유효한 purpose 값이 아닙니다."),
    NOT_EXIST_COMMENT(false, 2041, HttpStatus.OK, "존재하지 않는 댓글입니다."),
    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, HttpStatus.OK, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, HttpStatus.OK, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false, 3014, HttpStatus.OK, "없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_UPLOAD_S3(false, 3016, HttpStatus.OK, "S3 업로드 실패"),
    INVALID_IMAGE_FILE(false, 3017, HttpStatus.OK, "이미지 파일이 아닙니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 오류 입니다."),
    SERVER_ERROR(false, 4001, HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false, 4014, HttpStatus.OK, "유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, HttpStatus.OK, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, HttpStatus.OK, "비밀번호 복호화에 실패하였습니다."),
    KAKAO_PAY_READY_ERROR(false, 4123, HttpStatus.OK, "카카오페이 결제 정보를 가져오는데 실패했습니다."),
    EXISTS_PROGRESS(false, 4124, HttpStatus.OK, "이미 진행 중이거나 사람 선택을 완료했습니다."),
    NOT_EXIST_PROGRESS(false, 4126, HttpStatus.OK, "진행중인 거래가 없습니다."),
    NOT_EQUALS_POST(false, 4127, HttpStatus.OK, "거래하려는 같은 게시물이 아닙니다."),
    FAILED_SEND_MESSAGE(false, 4128, HttpStatus.OK,"fcm 메세지 전송에 실패했습니다." );


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final HttpStatus httpCode;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, HttpStatus httpCode, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.httpCode = httpCode;
        this.message = message;
    }
}
