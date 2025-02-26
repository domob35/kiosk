package guro.spring.kiosk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    // 각 메뉴의 옵션 포함 가격
    @Column(nullable = false)
    private int subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ProductOrder productOrder;

    @Builder
    public ProductOrderDetail(int quantity, Product product, ProductOrder productOrder) {
        this.quantity = quantity;
        this.product = product;
        this.productOrder = productOrder;
        this.subtotal = product.getPrice();
    }

    public void addSubtotal(int amount) {
        this.subtotal += amount;
    }

    public void applyQuantity() {
        this.subtotal *= this.quantity;
    }
}
