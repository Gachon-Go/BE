package gcu.mp.payclient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PayRequestResDto {
    String tid;
    String next_redirect_app_url;
    String next_redirect_mobile_url;
    String next_redirect_pc_url;
    String android_app_scheme;
    String ios_app_scheme;
    String created_at;
}
