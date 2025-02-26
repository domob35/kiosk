package guro.spring.kiosk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.ProductOrder;
import guro.spring.kiosk.entity.ProductOrderDetail;
import guro.spring.kiosk.entity.Recipe;
import guro.spring.kiosk.repository.ProductOrderDetailRepo;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOrderDetailService {
    private final ProductOrderDetailRepo productOrderDetailRepo;
    private final RecipeService recipeService;
    private final BranchInventoryService branchInventoryService;

    /**
     * 상품, 수량, 그리고 상품 주문에 대한 상세 정보를 생성하고, 해당 상품의 레시피에 따라 재료를 차감한 후,
     * 생성된 상품 주문 상세 정보를 저장합니다.
     *
     * @param product 상품
     * @param quantity 수량
     * @param productOrder 상품 주문
     * @return 생성 후 저장된 상품 주문 상세 정보
     */
    @Transactional
    public ProductOrderDetail createProductOrderDetailByProductAndQuantity(Product product, int quantity, ProductOrder productOrder) {
        ProductOrderDetail productOrderDetail = ProductOrderDetail.builder()
            .product(product)
            .quantity(quantity)
            .productOrder(productOrder)
            .build();

        List<Recipe> recipes = recipeService.getRecipesByProduct(product);
        
        for(Recipe recipe : recipes) {
            branchInventoryService.useIngredient(recipe.getIngredient(), recipe.getQuantity()*quantity, productOrder.getBranch());
        }

        return productOrderDetailRepo.save(productOrderDetail);
    }
}
