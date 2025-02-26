package guro.spring.kiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.ProductOrderDetailOption;

public interface ProductOrderDetailOptionRepo extends JpaRepository<ProductOrderDetailOption, Long> {
    
}
