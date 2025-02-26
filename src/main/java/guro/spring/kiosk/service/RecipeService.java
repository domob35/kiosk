package guro.spring.kiosk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Branch;
import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.entity.Recipe;
import guro.spring.kiosk.repository.RecipeRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepo recipeRepo;
    private final BranchInventoryService branchInventoryService;

    public List<Recipe> getRecipesByProduct(Product product) {
        return recipeRepo.findAllByProduct(product);
    }

    /**
     * 레시피의 재료가 매장에서 사용 가능한지 확인하는 메소드
     * 
     * @param branch 확인할 매장
     * @param recipe 확인할 레시피
     * @return 재료가 부족하거나 없으면 true, 사용 가능하면 false 반환
     */
    public boolean isRecipeUnavailable(Branch branch, Recipe recipe) {
        if (branchInventoryService.isIngredientUnavailable(branch, recipe.getIngredient())) {
            return true;
        }
        else if (branchInventoryService.isIngredientNotEnough(branch, recipe.getIngredient(), recipe.getQuantity())) {
            return true;
        }
        return false;
    }
}
