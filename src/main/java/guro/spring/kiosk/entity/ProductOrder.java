package guro.spring.kiosk.entity;

import java.time.LocalDateTime;

import guro.spring.kiosk.enums.PaymentMethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@NoArgsConstructor
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @Column(nullable = false)
    private LocalDateTime date; // 주문일자(날짜+시간)

    @Column(nullable = false)
    private int totalPrice; // 주문 원래 가격

    @Column(nullable = false)
    private int billedAmount; // 청구 금액

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // 결제 수단

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    // 외래 키 (ProductOrder : Branch = N : 1)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    // 외래 키 (ProductOrder : Member = N : 1)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Coupon coupon; // 외래 키 (ProductOrder : Coupon = 1 : 1)

    public ProductOrder(Branch branch) {
        this.date = LocalDateTime.now();
        this.branch = branch;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    public void setBilledAmount(int billedAmount) {
        this.billedAmount = billedAmount;
    }


    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void orderedMember(Member member) {
        this.member = member;
    }

    public void usedCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}

