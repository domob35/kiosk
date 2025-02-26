package guro.spring.kiosk.dto.resp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor

@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberCouponResp {
    private long couponId; // 쿠폰 기본키
    private String couponName; // 쿠폰 이름
    private int couponDiscount; // 쿠폰 할인 금액
}


