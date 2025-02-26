package guro.spring.kiosk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.ProductOption;

public interface ProductOptionRepo extends JpaRepository<ProductOption, Long> {
    List<ProductOption> findAllByProduct(Product product);
    List<ProductOption> findAllByProductAndOption_Group(Product product, String group);
}

