package guro.spring.kiosk.service;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Coupon;
import guro.spring.kiosk.entity.Member;
import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.ProductOrder;
import guro.spring.kiosk.exception.CustomEEnum;
import guro.spring.kiosk.exception.CustomException;
import guro.spring.kiosk.repository.CouponRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CouponService {
    private final CouponRepo couponRepo;
    
    public Coupon getCouponById(Long id) {
        return couponRepo.findById(id)
            .orElseThrow(() -> new CustomException(CustomEEnum.COUPON_NOT_FOUND));
    }

    public void useCoupon(Coupon coupon, ProductOrder productOrder) {
        if(coupon.getProductOrder() != null) {
            throw new CustomException(CustomEEnum.COUPON_ALREADY_USED);
        }
        coupon.useCoupon(productOrder);
        couponRepo.save(coupon);
    }

    public void createCoupon(Member member, Product product) {
        Coupon coupon = Coupon.builder()
            .member(member)
            .product(product)
            .build();
        couponRepo.save(coupon);
    }
}
