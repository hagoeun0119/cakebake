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
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    protected BasketProduct() {
    }

    public BasketProduct(Basket basket, OrderProduct orderProduct) {
        this.basket = basket;
        this.orderProduct = orderProduct;
    }
}
