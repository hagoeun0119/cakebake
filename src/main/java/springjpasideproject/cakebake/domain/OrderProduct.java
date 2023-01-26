package springjpasideproject.cakebake.domain;


import static jakarta.persistence.FetchType.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class OrderProduct {

    @Id @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

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
