package guro.spring.kiosk.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guro.spring.kiosk.dto.resp.MemberCouponResp;
import guro.spring.kiosk.dto.resp.MemberPointResp;
import guro.spring.kiosk.entity.Coupon;
import guro.spring.kiosk.entity.Member;
import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.exception.CustomEEnum;
import guro.spring.kiosk.exception.CustomException;
import guro.spring.kiosk.service.MemberService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {
    private final MemberService memberService;
    
    @GetMapping("/point/{phoneNumber}")
    public ResponseEntity<MemberPointResp> getPointByPhoneNumber(@PathVariable String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new CustomException(CustomEEnum.PHONE_NUMBER_REQUIRED);
        }

        Member member = memberService.getMemberByPhone(phoneNumber);

        return ResponseEntity.ok(MemberPointResp.builder()
            .point(member.getPoint())
            .id(member.getId())
            .build());
    }


    @GetMapping("/{id}/coupons")
    public ResponseEntity<List<MemberCouponResp>> getCouponsByMemberId(@PathVariable Long id) {
        if (id == null) {
            throw new CustomException(CustomEEnum.MEMBER_ID_REQUIRED);

        }
        
        Member member = memberService.getMemberById(id);
        List<MemberCouponResp> memberCoupons = new ArrayList<>();

        for(Coupon coupon : member.getCoupons()) {
            if(coupon.getProductOrder() == null) { // 쿠폰이 사용되지 않았다면
                Product product = coupon.getProduct();
                memberCoupons.add(MemberCouponResp.builder()
                    .couponId(coupon.getId())
                    .couponName(product.getName())
                    .couponDiscount(product.getPrice())
                    .build());
            }
        }

        return ResponseEntity.ok(memberCoupons);
    }
}