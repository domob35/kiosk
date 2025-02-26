package guro.spring.kiosk.entity;

import java.util.List;

import guro.spring.kiosk.enums.Authority;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String userName; // 이게 사용자 아이디임;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String realName;

    @Column(nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    // 유저별 총 결제 금액
    @Column(nullable = false)
    private long totalPaymentAmount;

    // 총 결제한 횟수
    @Column(nullable = false)
    private int totalPurchaseCount;

    // 스탬프 수
    @Column(nullable = false)
    private int stamp;

    // 적립금
    @Column(nullable = false)
    private int point;

    //외래키 membership 참조(n:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Membership membership;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Coupon> coupons;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    public void useMembershipPoint(int usedPoint) {
        this.point -= usedPoint;
    }

    public boolean stampUpdate() {
        this.stamp += 1;
        if(this.stamp == 10) {
            this.stamp = 0;
            return true;
        }
        return false;
    }

    public void billed(int billedAmount) {
        this.totalPaymentAmount += billedAmount;
        this.totalPurchaseCount += 1;
    }

    public void updateMembership(Membership membership) {
        this.membership = membership;
    }

    public void updateMembershipPoint(int billedAmount) {
        int point = (int) (billedAmount * this.membership.getPointRate());
        this.point += point;
    }
}