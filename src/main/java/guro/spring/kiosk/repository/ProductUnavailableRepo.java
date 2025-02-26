package guro.spring.kiosk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.Branch;
import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.ProductUnavailable;

public interface ProductUnavailableRepo extends JpaRepository<ProductUnavailable, Long> {
    boolean existsByBranchAndProduct(Branch branch, Product product);
    Optional<ProductUnavailable> findByBranchAndProduct(Branch branch, Product product);
}

