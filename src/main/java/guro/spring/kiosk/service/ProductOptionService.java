package guro.spring.kiosk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.ProductOption;
import guro.spring.kiosk.repository.ProductOptionRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductOptionService {
    private final ProductOptionRepo productOptionRepo;

    public List<ProductOption> getProductOptionsByProduct(Product product) {
        return productOptionRepo.findAllByProduct(product);
    }

    /**
     * 상품의 온도 옵션을 가져오는 메소드
     * @param product
     * @return "hot_only", "ice_only", "hot_and_ice" 중 하나를 반환.
     *         TEMPERATURE 그룹의 옵션이 없으면 "ice_only" 반환.
     *         TEMPERATURE 그룹의 옵션이 1개이고, 그 옵션이 "HOT"이면 "hot_only", 아니면 "ice_only" 반환.
     *         TEMPERATURE 그룹의 옵션이 2개 이상이면 "hot_and_ice" 반환.
     */
    public String getTemperatureOption(Product product) {
        List<ProductOption> temperatureOptions = productOptionRepo.findAllByProductAndOption_Group(product, "TEMPERATURE");
        if(temperatureOptions.isEmpty()) {
            return "ice_only";
        }
        if(temperatureOptions.size() == 1) {
            if(temperatureOptions.get(0).getOption().getName().equals("HOT")) {
                return "hot_only";

            } else {
                return "ice_only";
            }

        }
        return "hot_and_ice";
    }
}


