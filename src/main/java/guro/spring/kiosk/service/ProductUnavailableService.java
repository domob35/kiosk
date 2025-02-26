package guro.spring.kiosk.service;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Branch;
import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.ProductUnavailable;
import guro.spring.kiosk.repository.ProductUnavailableRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductUnavailableService {
    private final ProductUnavailableRepo productUnavailableRepo;

    public boolean isProductUnavailable(Branch branch, Product product) {
        return productUnavailableRepo.existsByBranchAndProduct(branch, product);
    }

    public void toggleProductUnavailable(Branch branch, Product product) {
        productUnavailableRepo.findByBranchAndProduct(branch, product)
            .ifPresentOrElse(
                productUnavailable -> productUnavailableRepo.delete(productUnavailable),
                () -> productUnavailableRepo.save(ProductUnavailable.builder()
                    .branch(branch)
                    .product(product)
                    .build()));
    }
}
