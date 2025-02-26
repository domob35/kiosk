package guro.spring.kiosk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import guro.spring.kiosk.dto.req.ProductOrderReq;
import guro.spring.kiosk.dto.resp.ProductOrderSuccessResp;
import guro.spring.kiosk.entity.ProductOrder;
import guro.spring.kiosk.exception.CustomEEnum;
import guro.spring.kiosk.exception.CustomException;
import guro.spring.kiosk.service.ProductOrderService;



import lombok.RequiredArgsConstructor;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductOrderController {
    private final ProductOrderService productOrderService;

    /**
     * 상품 주문을 생성하는 POST 요청을 처리합니다.
     *
     * @param req 상품 주문 요청 객체 (ProductOrderReq)
     *            - memberId: (선택 사항) 회원의 ID. 쿠폰 및 멤버십 포인트 사용을 위해 필요합니다. null이면 비회원 주문으로 간주합니다.
     *            - couponId: (선택 사항) 사용할 쿠폰의 ID.
     *            - membershipPoint: (선택 사항) 사용할 멤버십 포인트.
     *            - products: 주문에 포함된 상품 목록 (각 상품은 ID, 수량, 옵션 목록을 포함).
     *            - paymentMethod: 결제 방법 (예: "CARD", "CASH").
     *            - branchId: 주문이 이루어진 지점의 ID.
     * @return ResponseEntity<ProductOrderSuccessResp>
     *         - HTTP 상태 코드 200 (OK)와 함께 ProductOrderSuccessResp 객체를 반환합니다.
     *         - ProductOrderSuccessResp는 주문이 성공적으로 생성되었음을 나타내며, 지점 이름과 주문 번호를 포함합니다.
     * @throws CustomException
     *         - 회원 ID 없이 쿠폰이나 멤버십 포인트를 사용하려고 할 경우 USE_COUPON_OR_MEMBERSHIP_POINT_WITHOUT_MEMBER 예외가 발생합니다.
     */
    @PostMapping
    public ResponseEntity<ProductOrderSuccessResp> createProductOrder(@RequestBody ProductOrderReq req) {
        if(req.getMemberId() == null) {
            if(req.getCouponId() != null || req.getMembershipPoint() != null) {
                throw new CustomException(CustomEEnum.USE_COUPON_OR_MEMBERSHIP_POINT_WITHOUT_MEMBER);
            }
        }
        ProductOrder productOrder = productOrderService.createProductOrder(req);
        return ResponseEntity.ok(ProductOrderSuccessResp.builder()
            .branchName(productOrder.getBranch().getName())
            .orderNumber(productOrder.getId())
            .build());
    }
}


