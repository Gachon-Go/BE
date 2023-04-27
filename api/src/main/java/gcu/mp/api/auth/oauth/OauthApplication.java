package gcu.mp.api.auth.oauth;

import gcu.mp.oauthclient.provider.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthApplication {
    private final KakaoService kakaoService;

    @Operation(summary = "(서버전용)인가 코드로 토큰 받아오는 API ")
    @GetMapping("/login/oauth/code/kakao")
    public void getCodeAndToken(@RequestParam String code) {
        System.out.println(code);
        kakaoService.getToken(code);
    }
}
