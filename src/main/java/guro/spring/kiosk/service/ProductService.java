package guro.spring.kiosk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.dto.resp.OptionResp;
import guro.spring.kiosk.dto.resp.ProductDetailResp;
import guro.spring.kiosk.dto.resp.ProductSimpleResp;
import guro.spring.kiosk.entity.Branch;
import guro.spring.kiosk.entity.Option;
import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.ProductOption;
import guro.spring.kiosk.entity.Recipe;
import guro.spring.kiosk.exception.CustomEEnum;
import guro.spring.kiosk.exception.CustomException;
import guro.spring.kiosk.repository.ProductRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductOptionService productOptionService;
    private final ProductUnavailableService productUnavailableService;
    private final RecipeService recipeService;
    private final OptionService optionService;

    public List<Product> getProductsByCategory(String category) {
        return productRepo.findAllByCategory(category);
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new CustomException(CustomEEnum.PRODUCT_NOT_FOUND));
    }

    /**
     * 카테고리별로 상품 상세 정보를 가져오는 메소드.
     *
     * 
     * @param branch   지점 정보
     * @param category 카테고리 정보
     * @return 카테고리에 해당하는 상품 상세 정보 목록 (List of {@link ProductDetailResp})
     *         각 상품에 대해 다음 정보를 포함:
     *         - 상품 옵션 목록 (List of {@link OptionResp}) from
     *         {@link ProductOptionService#getProductOptionsByProduct(Product)}
     *         - 상품의 판매 불가능 여부 (from
     *         {@link ProductUnavailableService#isProductUnavailable(Branch, Product)}
     *         and {@link RecipeService#isRecipeUnavailable(Branch, Recipe)})
     *         - 상품의 온도 옵션 (from
     *         {@link ProductOptionService#getTemperatureOption(Product)})
     */
    public List<ProductDetailResp> getProductDetailsByCategory(Branch branch, String category) {

        // 주어진 카테고리에 해당하는 상품 목록을 가져옵니다.
        List<Product> products = getProductsByCategory(category);
        // 만약 상품 목록이 비어있다면, PRODUCT_EMPTY_IN_CATEGORY 예외를 발생시킵니다.
        if (products.isEmpty()) {
            throw new CustomException(CustomEEnum.PRODUCT_EMPTY_IN_CATEGORY);
        }

        List<ProductDetailResp> productDetailResps = new ArrayList<>();

        for (Product product : products) {
            // 상품의 옵션 목록을 가져옵니다.
            List<ProductOption> productOptions = productOptionService.getProductOptionsByProduct(product);
            List<Option> options = productOptions.stream().map(ProductOption::getOption).collect(Collectors.toList());

            // 만약 상품 옵션이 없다면, 모든 옵션을 가져옵니다.
            if (options.isEmpty()) {
                options = optionService.getAllOptions();
            }

            // 상품의 옵션 목록을 가져옵니다.
            List<OptionResp> optionResps = new ArrayList<>();

            // 상품의 레시피 목록을 가져옵니다.
            List<Recipe> recipes = recipeService.getRecipesByProduct(product);

            // 상품의 판매 불가능 여부를 확인합니다.
            boolean unavailable = productUnavailableService.isProductUnavailable(branch, product);

            // 레시피의 재료가 매장에서 사용 가능한지 확인합니다.
            for (Recipe recipe : recipes) {
                if (unavailable) {
                    break;
                }
                if (recipeService.isRecipeUnavailable(branch, recipe)) {
                    unavailable = true;
                }
            }

            // 상품의 옵션 목록을 가져옵니다.
            for (Option option : options) {
                optionResps.add(OptionResp.builder()
                        .id(option.getId())
                        .name(option.getName())
                        .price(option.getPrice())
                        .group(option.getGroup())
                        .multiSelect(option.isMultiSelect())
                        .build());
            }

            // 상품의 상세 정보를 가져옵니다.
            ProductDetailResp productDetailResp = ProductDetailResp.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDesc())
                    .imageUrl(product.getImage())
                    .category(product.getCategory())
                    .price(product.getPrice())
                    .calories(product.getCalories())
                    .carbohydrates(product.getCarbohydrates())
                    .protein(product.getProtein())
                    .fat(product.getFat())
                    .caffeine(product.getCaffeine())
                    .options(optionResps)
                    .temperatureOption(productOptionService.getTemperatureOption(product))
                    .unavailable(unavailable)
                    .barcode(product.getBarcode())
                    .build();

            productDetailResps.add(productDetailResp);
        }
        return productDetailResps;
    }

    /**
     * 주어진 카테고리와 지점에 따라 상품의 간단한 정보 목록을 반환하는 메서드입니다.
     *
     * @param branch   지점 정보
     * @param category 카테고리 정보
     * @return 카테고리에 해당하는 상품의 간단한 정보 목록 (List of {@link ProductSimpleResp}).
     *         각 상품에 대해 다음 정보를 포함합니다:
     *         - 상품 ID
     *         - 상품 이름
     *         - 상품 이미지 URL
     *         - 상품 가격
     *         - 상품 판매 불가능 여부
     */
    public List<ProductSimpleResp> getProductSimplesByCategory(Branch branch, String category) {
        List<Product> products = getProductsByCategory(category);
        List<ProductSimpleResp> productSimpleResps = new ArrayList<>();
        for (Product product : products) {
            productSimpleResps.add(ProductSimpleResp.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .imageUrl(product.getImage()).price(product.getPrice())
                    .unavailable(productUnavailableService.isProductUnavailable(branch, product))
                    .build());
        }
        return productSimpleResps;
    }

}
