package gcu.mp.payclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.common.exception.BaseException;
import gcu.mp.payclient.dto.KakaoApproveDto;
import gcu.mp.payclient.dto.PayRequestDto;
import gcu.mp.payclient.dto.PayRequestResDto;
import gcu.mp.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoPayService {
    private final RedisUtil redisUtil;
    @Value("${pay.provider.kakao.Authorization}")
    private String authorization;
    @Value("${pay.provider.kakao.cid}")
    private String cid;
    @Value("${pay.provider.kakao.ready.ready-url}")
    private String readyUrl;
    @Value("${pay.provider.kakao.approve.approve-url}")
    private String approveUrl;
    @Value("${pay.provider.kakao.ready.approval_url}")
    private String approvalUrl;
    @Value("${pay.provider.kakao.ready.cancel_url}")
    private String cancelUrl;
    @Value("${pay.provider.kakao.ready.fail_url}")
    private String failUrl;

    public PayRequestResDto payment(PayRequestDto payRequestDto) {
        PayRequestResDto payRequestResDto;
        try {
            String order_id = UUID.randomUUID().toString();
            // 보내는 부분
            URL address = new URL(readyUrl);
            HttpURLConnection connection = (HttpURLConnection) address.openConnection(); // 서버연결
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "KakaoAK " + authorization); // 어드민 키
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.setDoOutput(true); // 서버한테 전달할게 있는지 없는지
            String parameter = "cid=TC0ONETIME" // 가맹점 코드
                    + "&partner_order_id=" + order_id // 가맹점 주문번호
                    + "&partner_user_id=" + payRequestDto.getMemberId() // 가맹점 회원 id
                    + "&item_name=point" // 상품명
                    + "&quantity=1" // 상품 수량
                    + "&total_amount=" + payRequestDto.getPrice() // 총 금액
                    + "&vat_amount=0" // 부가세
                    + "&tax_free_amount=0" // 상품 비과세 금액
                    + "&approval_url=" + approvalUrl + "?partner_order_id=" + order_id // 결제 성공 시
                    + "&fail_url=" + failUrl // 결제 실패 시
                    + "&cancel_url=" + cancelUrl; // 결제 취소 시
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            bw.write(parameter);
            bw.flush();
            int result = connection.getResponseCode(); // 전송 잘 됐나 안됐나 번호를 받는다.
            InputStream receive; // 받다

            if (result == 200) {
                receive = connection.getInputStream();
            } else {
                receive = connection.getErrorStream();
            }
            // 읽는 부분
            InputStreamReader read = new InputStreamReader(receive); // 받은걸 읽는다.
            BufferedReader change = new BufferedReader(read);
            String a = change.readLine();
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(a);
            // 받는 부분
            payRequestResDto = PayRequestResDto.builder()
                    .tid(elem.get("tid").toString())
                    .next_redirect_app_url(elem.get("next_redirect_app_url").toString())
                    .next_redirect_mobile_url(elem.get("next_redirect_mobile_url").toString())
                    .next_redirect_pc_url(elem.get("next_redirect_pc_url").toString())
                    .android_app_scheme(elem.get("android_app_scheme").toString())
                    .ios_app_scheme(elem.get("ios_app_scheme").toString())
                    .created_at(elem.get("created_at").toString()).build(); // 문자열로 형변환을 알아서 해주고 찍어낸다 그리고 본인은 비워진다.
            redisUtil.setDataExpire("kakaoPay " + order_id, payRequestDto.getMemberId() + " " + payRequestResDto.getTid(), 60 * 10L);
            return payRequestResDto;
        } catch (IOException | ParseException e) {
            throw new BaseException(BaseResponseStatus.KAKAO_PAY_READY_ERROR);
        }
    }


    public KakaoApproveDto paySuccess(String partner_order_id, String pgToken) {
        try {
            String[] payData = redisUtil.getData("kakaoPay " + partner_order_id).split(" ");
            URL address = new URL(approveUrl);
            HttpURLConnection connection = (HttpURLConnection) address.openConnection(); // 서버연결
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "KakaoAK " + authorization); // 어드민 키
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.setDoOutput(true); // 서버한테 전달할게 있는지 없는지
            String parameter = "cid=TC0ONETIME" // 가맹점 코드
                    + "&tid=" + payData[1]  // tid
                    + "&partner_order_id=" + partner_order_id // 가맹점 주문번호
                    + "&partner_user_id=" + payData[0] // 가맹점 회원 id
                    + "&pg_token=" + pgToken; // 결제 토큰
            OutputStream send = connection.getOutputStream(); // 이제 뭔가를 를 줄 수 있다.
            DataOutputStream dataSend = new DataOutputStream(send); // 이제 데이터를 줄 수 있다.
            dataSend.writeBytes(parameter); // OutputStream은 데이터를 바이트 형식으로 주고 받기로 약속되어 있다. (형변환)
            dataSend.close(); // flush가 자동으로 호출이 되고 닫는다. (보내고 비우고 닫다)
            int result = connection.getResponseCode(); // 전송 잘 됐나 안됐나 번호를 받는다.
            InputStream receive; // 받다

            if (result == 200) {
                receive = connection.getInputStream();
            } else {
                receive = connection.getErrorStream();
            }
            // 읽는 부분
            InputStreamReader read = new InputStreamReader(receive); // 받은걸 읽는다.
            BufferedReader change = new BufferedReader(read);
            String a = change.readLine();
            JsonElement elem = JsonParser.parseString(a);
            return KakaoApproveDto.builder()
                    .aid(elem.getAsJsonObject().get("aid").getAsString())
                    .amount(KakaoApproveDto.Amount.builder()
                            .total(elem.getAsJsonObject().get("amount").getAsJsonObject().get("total").getAsInt())
                            .tax_free(elem.getAsJsonObject().get("amount").getAsJsonObject().get("tax_free").getAsInt())
                            .build())
                    .approved_at(elem.getAsJsonObject().get("approved_at").getAsString())
                    .cid(elem.getAsJsonObject().get("cid").getAsString())
                    .item_name(elem.getAsJsonObject().get("item_name").getAsString())
                    .partner_order_id(elem.getAsJsonObject().get("partner_order_id").getAsString())
                    .partner_user_id(elem.getAsJsonObject().get("partner_user_id").getAsString())
                    .payment_method_type(elem.getAsJsonObject().get("payment_method_type").getAsString())
                    .created_at(elem.getAsJsonObject().get("created_at").getAsString())
                    .tid(elem.getAsJsonObject().get("tid").getAsString()).build();
        } catch (IOException e) {
            throw new BaseException(BaseResponseStatus.KAKAO_PAY_READY_ERROR);
        }
    }
}