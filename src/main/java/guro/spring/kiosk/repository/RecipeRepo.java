package guro.spring.kiosk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.Recipe;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByProduct(Product product);
}

