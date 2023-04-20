package gcu.project.gachongo.api.auth.oauth;

import gcu.project.gachongo.service.oauth.provider.KakaoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthApplication {
    private final KakaoService kakaoService;

    @ApiIgnore
    @ApiOperation("(서버전용)인가 코드로 토큰 받아오는 API ")
    @GetMapping("/login/oauth/code/kakao")
    public void getCodeAndToken(@RequestParam String code) {
        System.out.println(code);
        kakaoService.getToken(code);
    }
}
