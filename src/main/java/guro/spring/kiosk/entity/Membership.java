package guro.spring.kiosk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 멤버십 ID (기본키)

    @Column(nullable = false)
    private String name; // 멤버십 이름

    @Column(nullable = false)
    private long requiredPaymentAmount; // 멤버십을 위한 총 결제 금액

    @Column(nullable = false)
    private double pointRate; // 적립률 (0.01 = 1%)

    @OneToOne(fetch = FetchType.LAZY)
    private Product product; // 멤버십 상품
}
