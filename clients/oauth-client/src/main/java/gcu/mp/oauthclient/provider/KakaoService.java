package gcu.mp.oauthclient.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.common.exception.BaseException;
import gcu.mp.oauthclient.dto.kakao.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client_id;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String client_secret;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_uri;
    @Value("${spring.security.oauth2.client.registration.kakao.client-authentication-method}")
    private String client_authentication_method;
    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authorization_uri;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String token_uri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String user_info_uri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-name-attribute}")
    private String user_name_attribute;

    public void getToken(String code) {

        try {
            // 인가코드로 토큰받기
            URL url = new URL(token_uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            StringBuilder result;
            urlConnection.setRequestMethod(client_authentication_method);
            urlConnection.setDoOutput(true); // 데이터 기록 알려주기
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            String sb = "grant_type=authorization_code" +
                    "&client_id=" + client_id +
                    "&redirect_uri=" + redirect_uri +
                    "&code=" + code +
                    "&client_secret=" + client_secret;

            bw.write(sb);
            bw.flush();
            int responseCode = urlConnection.getResponseCode();
            System.out.println(urlConnection.getResponseMessage());
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("result = " + result);
            // json parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(result.toString());
            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();
            System.out.println("refresh_token = " + refresh_token);
            System.out.println("access_token = " + access_token);
            br.close();
            bw.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * access token으로 유저 정보 가져오기
     *
     * @param token //access token
     * @return // 유저 정보 AccessTokenInfoDto 형태로 리턴
     */
    public KakaoUserInfo getUserInfoByKakaoToken(String token)  {


        //access_token을 이용하여 사용자 정보 조회
        KakaoUserInfo kakaoUserInfo;

        URL url;
        try {
            url = new URL(user_info_uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(client_authentication_method);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            if(responseCode==401)
                throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            // jackson objectmapper 객체 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON String -> Map
            Map<String, Object> jsonMap = objectMapper.readValue(result.toString(), new TypeReference<>() {
            });
            //Gson 라이브러리로 JSON파싱
            //accesstoken 정보 Dto에 빌드
            kakaoUserInfo = new KakaoUserInfo(jsonMap);
            br.close();
            return kakaoUserInfo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}