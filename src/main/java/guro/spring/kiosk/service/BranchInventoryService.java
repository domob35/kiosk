package guro.spring.kiosk.service;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Branch;
import guro.spring.kiosk.entity.BranchInventory;
import guro.spring.kiosk.entity.Ingredient;
import guro.spring.kiosk.exception.CustomEEnum;
import guro.spring.kiosk.exception.CustomException;
import guro.spring.kiosk.repository.BranchInventoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchInventoryService {
    private final BranchInventoryRepo branchInventoryRepo;

    public BranchInventory getBranchInventoryByBranchAndIngredient(Branch branch, Ingredient ingredient) {
        return branchInventoryRepo.findByBranchAndIngredient(branch, ingredient)
                .orElseThrow(() -> new CustomException(CustomEEnum.INGREDIENT_NOT_FOUND));
    }

    /**
     * 지점과 재료를 받아서 재고가 null이면 true를 반환하고, 그렇지 않으면 false를 반환합니다.
     * 즉, 재고가 없으면(null) 사용할 수 없는 것으로 간주합니다.
     *
     * @param branch     지점
     * @param ingredient 재료
     * @return 재료를 사용할 수 없으면 true, 사용할 수 있으면 false
     */
    public boolean isIngredientUnavailable(Branch branch, Ingredient ingredient) {
        try {
            getBranchInventoryByBranchAndIngredient(branch, ingredient);
            return false;
        } catch (CustomException e) {
            return true;
        }
    }

    /**
     * 특정 지점에 특정 재료의 수량이 필요한 수량보다 적은지 확인합니다.
     *
     * @param branch         지점
     * @param ingredient     재료
     * @param neededQuantity 필요한 수량
     * @return 재료의 수량이 필요한 수량보다 적으면 true, 그렇지 않으면 false. 재료를 찾을 수 없는 경우에도 true를
     *         반환합니다.
     */
    public boolean isIngredientNotEnough(Branch branch, Ingredient ingredient, int neededQuantity) {
        try {
            BranchInventory branchInventory = getBranchInventoryByBranchAndIngredient(branch, ingredient);
            return isIngredientNotEnough(branchInventory, neededQuantity);
        } catch (CustomException e) {
            return true;
        }

    }

    /**
     * 재료의 수량이 필요한 수량보다 적은지 확인합니다.
     *
     * @param branchInventory 지점 재고
     * @param neededQuantity  필요한 수량
     * @return 재료의 수량이 필요한 수량보다 적으면 true, 그렇지 않으면 false
     */
    public boolean isIngredientNotEnough(BranchInventory branchInventory, int neededQuantity) {
        return branchInventory.getQuantity() < neededQuantity;
    }

    /**
     * 지정된 지점에서 특정 재료의 재고를 주어진 양만큼 사용합니다.
     *
     * @param ingredient   사용할 재료
     * @param usedQuantity 사용되는 재료의 양
     * @param branch       재료를 사용할 지점
     * @throws CustomException 재료를 찾을 수 없거나 재고가 부족한 경우 예외를 발생시킵니다.
     */
    @Transactional
    public void useIngredient(Ingredient ingredient, int usedQuantity, Branch branch) {
        BranchInventory branchInventory = getBranchInventoryByBranchAndIngredient(branch, ingredient);
        if (branchInventory == null) {
            throw new CustomException(CustomEEnum.INGREDIENT_NOT_FOUND);
        }
        if (isIngredientNotEnough(branchInventory, usedQuantity)) {
            throw new CustomException(CustomEEnum.INGREDIENT_NOT_ENOUGH);
        }
        branchInventory.minusQuantity(usedQuantity);
        branchInventoryRepo.save(branchInventory);
    }
}
