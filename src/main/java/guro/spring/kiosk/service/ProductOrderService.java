package guro.spring.kiosk.service;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.dto.req.ProductOrderReq;
import guro.spring.kiosk.entity.Branch;
import guro.spring.kiosk.entity.Coupon;
import guro.spring.kiosk.entity.Member;
import guro.spring.kiosk.entity.Option;
import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.ProductOrder;
import guro.spring.kiosk.entity.ProductOrderDetail;
import guro.spring.kiosk.enums.PaymentMethod;
import guro.spring.kiosk.repository.ProductOrderRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductOrderService {
    private final ProductOrderRepo productOrderRepo;
    private final ProductService productService;
    private final OptionService optionService;
    private final ProductOrderDetailService productOrderDetailService;
    private final ProductOrderDetailOptionService productOrderDetailOptionService;
    private final BranchService branchService;
    private final MemberService memberService;
    private final CouponService couponService;

    /**
     * 주문 정보를 기반으로 상품 주문을 생성하는 서비스입니다.
     * 이 메서드는 다음 작업을 수행합니다:
     * 1. 총 가격과 결제 금액을 초기화합니다.
     * 2. 요청된 결제 방법을 가져옵니다.
     * 3. 요청된 지점 ID를 기반으로 지점 정보를 가져옵니다.
     * 4. 지점 정보를 기반으로 새로운 ProductOrder 엔티티를 생성합니다.
     * 5. 주문에 포함된 각 상품에 대해 반복합니다:
     *    - 상품 ID를 기반으로 상품 정보를 가져옵니다.
     *    - 상품, 수량, 주문 정보를 기반으로 상품 주문 상세 정보(ProductOrderDetail)를 생성합니다.
     *    - 상품에 대한 각 옵션을 처리합니다:
     *      - 옵션 ID를 기반으로 옵션 정보를 가져옵니다.
     *      - 상품 주문 상세 정보와 옵션 정보를 기반으로 상품 주문 상세 옵션 정보(ProductOrderDetailOption)를 생성합니다.
     *      - 옵션 가격을 상품 주문 상세 정보의 소계에 추가합니다.
     *    - 수량을 상품 주문 상세 정보에 적용합니다. (ProductOrderDetail의 applyQuantity()메서드)
     *    - 상품 주문 상세 정보의 소계를 총 가격에 추가합니다.
     * 6. 초기 결제 금액을 총 가격으로 설정합니다.
     * 7. 회원 ID가 제공된 경우, 쿠폰과 멤버십 포인트를 사용할 수 있는지 확인합니다:
     *    - 회원 ID를 기반으로 회원 정보를 가져옵니다.
     *    - 쿠폰 ID가 제공된 경우, 쿠폰을 사용합니다:
     *      - 쿠폰 ID를 기반으로 쿠폰 정보를 가져옵니다.
     *      - 쿠폰을 사용 처리합니다. (CouponService의 useCoupon()메서드)
     *      - ProductOrder에 쿠폰을 사용했음을 알립니다.
     *      - 결제 금액에서 쿠폰 할인 금액을 차감합니다.
     *    - 멤버십 포인트가 제공된 경우, 멤버십 포인트를 사용합니다:
     *      - 회원 정보와 사용할 멤버십 포인트를 기반으로 멤버십 포인트를 사용 처리합니다. (MemberService의 useMembershipPoint()메서드)
     *      - 결제 금액에서 멤버십 포인트만큼 차감합니다.
     *    - ProductOrder에 회원 정보를 연결합니다.
     * 8. 결제 정보를 ProductOrder에 저장합니다:
     *    - 총 가격을 설정합니다.
     *    - 최종 결제 금액을 설정합니다.
     *    - 결제 방법을 설정합니다.
     *    - ProductOrder를 데이터베이스에 저장합니다.
     * 9. 회원 ID가 제공된 경우, 결제 후 정보를 저장합니다:
     *    - 회원 ID와 ProductOrder를 기반으로 결제 후 작업을 처리합니다. (MemberService의 didOrder()메서드)
     *
     * @param req ProductOrderReq 객체. 주문 생성에 필요한 모든 정보를 포함합니다.
     *            - products: 주문에 포함된 상품 목록 (각 상품은 ID, 수량, 옵션 목록을 포함)
     *            - memberId: (선택 사항) 회원의 ID. 쿠폰 및 멤버십 포인트 사용을 위해 필요합니다.
     *            - couponId: (선택 사항) 사용할 쿠폰의 ID.
     *            - membershipPoint: (선택 사항) 사용할 멤버십 포인트.
     *            - paymentMethod: 결제 방법 (예: "CARD", "CASH").
     *            - branchId: 주문이 이루어진 지점의 ID.
     * @Transactional: 이 메서드의 모든 작업은 하나의 트랜잭션으로 처리됩니다.
     *                 어떤 작업이라도 실패하면, 모든 변경 사항이 롤백됩니다.
     */
    @Transactional
    public ProductOrder createProductOrder(ProductOrderReq req) {
        int totalPrice = 0;
        int billedAmount = 0;
        PaymentMethod paymentMethod = req.getPaymentMethod();
        Branch branch = branchService.getBranchById(req.getBranchId());
        ProductOrder productOrder = new ProductOrder(branch);

        // 임시값 저장
        productOrder.setTotalPrice(totalPrice);
        productOrder.setBilledAmount(billedAmount);
        productOrder.setPaymentMethod(paymentMethod);
        productOrderRepo.save(productOrder);
        
        // 각 상품에 대해 반복
        for(ProductOrderReq.ProductReq productReq : req.getProducts()) {
            // 상품 ID를 기반으로 상품 정보를 가져옴
            Product product = productService.getProductById(productReq.getId());
            // 상품, 수량, 주문 정보를 기반으로 상품 주문 상세 정보를 생성
            ProductOrderDetail productOrderDetail = productOrderDetailService
                    .createProductOrderDetailByProductAndQuantity(product, productReq.getQuantity(), productOrder);
            // 각 상품에 대한 옵션 처리
            productReq.getOptions().forEach(optionReq -> {
                // 옵션 ID를 기반으로 옵션 정보를 가져옴
                Option option = optionService.getOptionById(optionReq.getId());
                // 상품 주문 상세 정보와 옵션 정보를 기반으로 상품 주문 상세 옵션 정보를 생성
                productOrderDetailOptionService
                        .createProductOrderDetailOptionByOption(productOrderDetail, option);
                // 옵션 가격을 상품 주문 상세 정보의 소계에 추가
                productOrderDetail.addSubtotal(option.getPrice());
            });
            // 수량을 상품 주문 상세 정보에 적용
            productOrderDetail.applyQuantity();
            // 상품 주문 상세 정보의 소계를 총 가격에 추가
            totalPrice += productOrderDetail.getSubtotal();
        }

        billedAmount = totalPrice;

        // 만약 memberId가 있으면 쿠폰과 멤버십 포인트를 사용할 수 있는지 확인
        if(req.getMemberId() != null) {
            Member member = memberService.getMemberById(req.getMemberId());
            // 쿠폰이 있으면 쿠폰을 사용
            if(req.getCouponId() != null) {
                Coupon coupon = couponService.getCouponById(req.getCouponId());
                couponService.useCoupon(coupon, productOrder);
                productOrder.usedCoupon(coupon);
                billedAmount -= coupon.getProduct().getPrice();
            }
            // 멤버십 포인트가 있으면 멤버십 포인트를 사용
            if(req.getMembershipPoint() != null) {
                memberService.useMembershipPoint(member, req.getMembershipPoint());
                billedAmount -= req.getMembershipPoint();
            }
            productOrder.orderedMember(member);
        }

        // 결제 정보 ProductOrder에 저장
        productOrder.setTotalPrice(totalPrice);
        productOrder.setBilledAmount(billedAmount);
        productOrder.setPaymentMethod(paymentMethod);
        productOrderRepo.save(productOrder);

        // 멤버가 있다면 결제 후 정보 저장
        if(req.getMemberId() != null) {
            memberService.didOrder(req.getMemberId(), productOrder);
        }

        return productOrder;
    }
}
