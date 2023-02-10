package springjpasideproject.cakebake.domain;


import static jakarta.persistence.FetchType.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "orderProduct",  cascade = CascadeType.ALL)
    private List<BasketProduct> basketProducts = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    public static OrderProduct createOrderProduct(Product product, int orderPrice, int count) {

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setOrderPrice(orderPrice);
        orderProduct.setCount(count);

        product.removeStock(count);
        return orderProduct;
    }

    public void cancel() { getProduct().addStock(count); }

    public int getTotalPrice() { return getOrderPrice() * getCount(); }

}
