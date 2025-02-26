package guro.spring.kiosk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.Branch;
import guro.spring.kiosk.entity.BranchInventory;
import guro.spring.kiosk.entity.Ingredient;


public interface BranchInventoryRepo extends JpaRepository<BranchInventory, Long> {
   Optional<BranchInventory> findByBranchAndIngredient(Branch branch, Ingredient ingredient);
}
