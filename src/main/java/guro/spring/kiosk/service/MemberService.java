package guro.spring.kiosk.service;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Member;
import guro.spring.kiosk.entity.Membership;
import guro.spring.kiosk.entity.ProductOrder;
import guro.spring.kiosk.exception.CustomEEnum;
import guro.spring.kiosk.exception.CustomException;
import guro.spring.kiosk.repository.MemberRepo;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepo memberRepo;
    private final CouponService couponService;
    private final MembershipService membershipService;
    public Member getMemberByPhone(String phone) {
        return memberRepo.findByPhone(phone)
            .orElseThrow(() -> new CustomException(CustomEEnum.MEMBER_NOT_FOUND));
    }

    public Member getMemberById(Long memberId) {
        return memberRepo.findById(memberId)
            .orElseThrow(() -> new CustomException(CustomEEnum.MEMBER_NOT_FOUND));
    }

    public void useMembershipPoint(Member member, int usedPoint) {
        if(member.getPoint() < usedPoint) {
            throw new CustomException(CustomEEnum.MEMBER_POINT_NOT_ENOUGH);
        }
        member.useMembershipPoint(usedPoint);
        memberRepo.save(member);
    }

    public void didOrder(Long memberId, ProductOrder productOrder) {
        Member member = getMemberById(memberId);
        didOrder(member, productOrder);
    }

    /**
     * 회원이 주문을 완료했을 때 호출되는 메서드입니다.
     * 다음 작업을 수행합니다:
     * 1. 회원의 총 결제 금액과 총 구매 횟수를 업데이트합니다.
     * 2. 스탬프를 업데이트하고, 스탬프가 최대치(10개)에 도달하면 쿠폰을 생성합니다.
     * 3. 멤버십 등급을 확인하고, 등급 조건이 충족되면 멤버십 등급을 업데이트합니다.
     * 4. 결제 금액에 따라 멤버십 포인트를 업데이트합니다.
     * 5. 변경된 회원 정보를 저장합니다.
     *
     * @param member 주문을 완료한 회원
     * @param productOrder 완료된 주문
     */
    public void didOrder(Member member, ProductOrder productOrder) {
        member.billed(productOrder.getBilledAmount());

        boolean isStampMax = member.stampUpdate();
        if(isStampMax) {
            couponService.createCoupon(member, member.getMembership().getProduct());
        }
        
        Membership membership = membershipService.checkMembership(member);
        if(membership != null) {
            member.updateMembership(membership);
        }

        member.updateMembershipPoint(productOrder.getBilledAmount());
        memberRepo.save(member);
    }
}
