package guro.spring.kiosk.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordReq {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}


