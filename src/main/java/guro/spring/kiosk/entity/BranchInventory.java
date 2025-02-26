package guro.spring.kiosk.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class BranchInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Ingredient ingredient;

    //재고 판매 불가 옵션
    @Column(nullable = false)
    private boolean unavailable;

    public void minusQuantity(int usedQuantity) {
        this.quantity -= usedQuantity;
    }
}

