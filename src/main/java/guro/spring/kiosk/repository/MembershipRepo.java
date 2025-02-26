package guro.spring.kiosk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.Membership;

public interface MembershipRepo extends JpaRepository<Membership, Long> {
    // 총 결제 금액이 매게변수 보다 낮은 멤버십 중 가장 높은 멤버십 조회
    Optional<Membership> findFirstByRequiredPaymentAmountLessThanOrderByRequiredPaymentAmountDesc(
            long totalPaymentAmount);
}
