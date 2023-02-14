package springjpasideproject.cakebake.domain;

import static jakarta.persistence.FetchType.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import springjpasideproject.cakebake.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @OneToMany(mappedBy = "Product",  cascade = CascadeType.ALL)
    private List<BasketProduct> basketProducts = new ArrayList<>();

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;
    private String ingredient;
    private String image;
    private int price;
    private int stockQuantity;

    // 비즈니스 로직
    /**
     * stock 증가
     */
    public void addStock(int quantity) { this.stockQuantity += quantity; }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more Stock");
        }
        this.stockQuantity = restStock;
    }
}
