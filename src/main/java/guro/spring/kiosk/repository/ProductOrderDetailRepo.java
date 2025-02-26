package guro.spring.kiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.ProductOrderDetail;

public interface ProductOrderDetailRepo extends JpaRepository<ProductOrderDetail, Long> {
    
}
