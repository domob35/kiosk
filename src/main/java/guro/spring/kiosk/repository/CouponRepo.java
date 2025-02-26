package guro.spring.kiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Long> {
    
}
