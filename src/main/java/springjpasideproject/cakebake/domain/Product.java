package springjpasideproject.cakebake.domain;

import static jakarta.persistence.FetchType.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springjpasideproject.cakebake.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static Product createProduct(String name, String ingredient, String image, int price, int stockQuantity, Category category) {

        Product product = new Product();
        product.name = name;
        product.ingredient = ingredient;
        product.image = image;
        product.price = price;
        product.stockQuantity = stockQuantity;
        product.category = category;
        return product;
    }

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
