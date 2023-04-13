package gcu.project.gachongo.global.config.SecurityConfig.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import gcu.project.gachongo.domain.member.repository.MemberRepository;

import gcu.project.gachongo.domain.member.vo.AccessToken;
import gcu.project.gachongo.global.common.api.ApiResponse;
import gcu.project.gachongo.global.common.api.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String username = authentication.getName();
        String accessToken = jwtTokenProvider.createAccessToken(username);
        AccessToken token = new AccessToken(accessToken);
        ApiResponse<AccessToken> result = new ApiResponse<>(ResponseCode.USER_LOGIN, token);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), result);
    }

}