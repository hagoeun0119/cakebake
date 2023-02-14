package springjpasideproject.cakebake.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class BasketProduct {

    @Id @GeneratedValue
    @Column(name = "basket_product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product Product;

    private int count;

    protected BasketProduct() {
    }

    public BasketProduct(Basket basket, Product Product, int count) {
        this.basket = basket;
        this.Product = Product;
        this.count = count;
    }
}
