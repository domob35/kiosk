package guro.spring.kiosk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(String category);
}

