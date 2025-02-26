package guro.spring.kiosk.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSimpleResp {
    private long id;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    private int price;
    private boolean unavailable;
}


