package guro.spring.kiosk.service;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Option;
import guro.spring.kiosk.entity.ProductOrderDetail;
import guro.spring.kiosk.entity.ProductOrderDetailOption;
import guro.spring.kiosk.repository.ProductOrderDetailOptionRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor


public class ProductOrderDetailOptionService {
    private final ProductOrderDetailOptionRepo productOrderDetailOptionRepo;

    /**
     * 상품 주문 상세 정보와 옵션을 기반으로 상품 주문 상세 옵션을 생성하고 저장합니다.
     *
     * @param productOrderDetail 상품 주문 상세 정보
     * @param option 옵션
     * @return 생성된 상품 주문 상세 옵션
     */
    @Transactional
    public ProductOrderDetailOption createProductOrderDetailOptionByOption(ProductOrderDetail productOrderDetail, Option option) {
        ProductOrderDetailOption productOrderDetailOption = ProductOrderDetailOption.builder()
                .productOrderDetail(productOrderDetail)
                .option(option)
                .build();
        return productOrderDetailOptionRepo.save(productOrderDetailOption);
    }
}
