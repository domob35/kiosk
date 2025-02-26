package guro.spring.kiosk.dto.req;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductUnavailableReq {
    @NotNull(message = "상품이 필요합니다.")
    private Long productId;
    @NotNull(message = "지점이 필요합니다.")
    private Long branchId;
}
