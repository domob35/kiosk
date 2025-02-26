package guro.spring.kiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.ProductOrder;

public interface ProductOrderRepo extends JpaRepository<ProductOrder, Long> {
    
}
