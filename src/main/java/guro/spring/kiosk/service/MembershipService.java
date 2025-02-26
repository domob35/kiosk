package guro.spring.kiosk.service;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Member;
import guro.spring.kiosk.entity.Membership;
import guro.spring.kiosk.repository.MembershipRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepo membershipRepo;

    /**
     * 회원의 총 결제 금액을 기반으로 멤버십 등급을 확인하고, 새로운 멤버십 등급이 있으면 반환합니다.
     *
     * @param member 멤버십 등급을 확인할 회원
     * @return 새로운 멤버십 등급이 있으면 해당 등급을 반환하고, 그렇지 않으면 null 반환.
     *         현재 멤버십과 새로운 멤버십 등급이 같으면 null을 반환.
     */
    public Membership checkMembership(Member member) {
        long totalPaymentAmount = member.getTotalPaymentAmount();
        Membership membership = membershipRepo
                .findFirstByRequiredPaymentAmountLessThanOrderByRequiredPaymentAmountDesc(totalPaymentAmount)
                .orElse(null);
        if (membership == null) {
            return null;
        }
        if (membership.getId().equals(member.getMembership().getId())) {
            return null;
        }
        return membership;
    }
}
