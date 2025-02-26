package guro.spring.kiosk.dto.resp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPointResp {
    private long id; // 회원 기본키
    private int point; // 회원 적립금
}



