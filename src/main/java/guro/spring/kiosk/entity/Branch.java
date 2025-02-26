package guro.spring.kiosk.entity;

import guro.spring.kiosk.enums.Authority;
import guro.spring.kiosk.enums.BranchStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 지점 ID

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 지점 이름

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private String phone; //전화번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority; // 권한 : ROLE_USER, ROLE_ADMIN

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BranchStatus status; // 지점 상태

    @Column(nullable = false)
    private String devicePw; // 지점별 키오스크 / 테이블 오더 관리자 패스워드

    public void updateDevicePw(String password) {
        this.devicePw = password;
    }
}
