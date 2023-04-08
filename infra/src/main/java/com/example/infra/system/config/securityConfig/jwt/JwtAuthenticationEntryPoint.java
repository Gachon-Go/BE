package com.example.infra.system.config.securityConfig.jwt;

import com.example.core.common.error.ErrorCode;
import com.example.core.common.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
//        ErrorCode errorCode = ErrorCode.HANDLE_UNAUTHORIZED;
//        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(errorCode);
//        ApiResult<Void> result = ApiResult.<Void>builder()
//                .success(false)
//                .error(errorResponseDto)
//                .build();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        log.info("JWT Authentication Entry Point {}", response);
        objectMapper.writeValue(response.getWriter(), new ErrorResponse( ErrorCode.UNAUTHORIZED));
    }
}