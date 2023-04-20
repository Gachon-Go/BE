package gcu.project.gachongo.global.common.exception;

import gcu.project.gachongo.global.common.api.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}