package guro.spring.kiosk.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 재료 ID

    @Column(nullable = false)
    private String name; // 재료 이름

    @Column(nullable = false)
    private int price; // 재료 가격
}
