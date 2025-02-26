package guro.spring.kiosk.dto.resp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResp {
    private long id; // 제품 기본키
    private String name; // 제품 이름
    private String description; // 제품 설명
    @JsonProperty("image_url") 
    private String imageUrl; // 제품 이미지 주소
    private String category; // 제품 카테고리
    private int price; // 제품 가격
    private int calories; // 제품 칼로리
    private int carbohydrates; // 제품 탄수화물
    private int protein; // 제품 단백질
    private int fat; // 제품 지방
    private int caffeine; // 제품 카페인
    private List<OptionResp> options; // 제품 옵션
    private boolean unavailable; // 제품 품절 여부
    @JsonProperty("temperature_option")
    private String temperatureOption; // 제품 온도 옵션
    private String barcode; // 제품 바코드
}
