package guro.spring.kiosk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product; // 외래 키 (coupon : product = N : 1 )

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member; // 외래 키 (coupon : member = N : 1 )

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private ProductOrder productOrder; // 외래 키 (coupon : productOrder = 1 : 1 )

    @Builder
    public Coupon(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public void useCoupon(ProductOrder productOrder) {
        this.productOrder = productOrder;
    }

}
