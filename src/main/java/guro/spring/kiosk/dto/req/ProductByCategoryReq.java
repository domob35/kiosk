package guro.spring.kiosk.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductByCategoryReq {
    @NotBlank(message = "카테고리가 필요합니다.")
    @JsonProperty("category")
    private String category; // 카테고리

    @NotNull(message = "지점이 필요합니다.")
    private Long branchId; // 지점 기본키
}



