package guro.spring.kiosk.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionResp {
    private long id; // 옵션 기본키
    private String name; // 옵션 이름
    private int price; // 옵션 가격
    private String group; // 옵션 그룹
    @JsonProperty("multi_select")
    private boolean multiSelect; // 옵션 다중 선택 여부
}



