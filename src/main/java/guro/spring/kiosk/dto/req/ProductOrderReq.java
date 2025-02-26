package guro.spring.kiosk.dto.req;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import guro.spring.kiosk.enums.PaymentMethod;
import guro.spring.kiosk.exception.CustomEEnum;
import guro.spring.kiosk.exception.CustomException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductOrderReq {
    @NotNull(message = "상품 정보가 없습니다.")
    private List<ProductReq> products; // 상품 정보
    private Long memberId; // 회원 기본키
    private Long couponId; // 쿠폰 기본키
    private Integer membershipPoint; // 멤버십 포인트
    @NotBlank(message = "결제 방법이 없습니다.")
    private String paymentMethod; // 결제 방법
    @NotNull(message = "지점 정보가 없습니다.")
    private Long branchId; // 지점 기본키

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ProductReq {
        @NotNull(message = "상품 정보가 없습니다.")
        private Long id; // 상품 기본키
        @NotNull(message = "상품 수량이 없습니다.")
        private Integer quantity; // 상품 수량
        private List<OptionReq> options; // 상품 옵션
    }

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OptionReq {
        @NotNull(message = "옵션 정보가 없습니다.")
        private Long id; // 옵션 기본키
    }

    public PaymentMethod getPaymentMethod() {
        try {
            return PaymentMethod.valueOf(paymentMethod.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(CustomEEnum.INVALID_PAYMENT_METHOD);
        }
    }
}

