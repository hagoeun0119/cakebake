package springjpasideproject.cakebake.domain;


import static jakarta.persistence.FetchType.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public OrderProduct(Product product, int orderPrice, int count) {
        this.product = product;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderProduct createOrderProduct(Product product, int orderPrice, int count) {
        OrderProduct orderProduct = new OrderProduct(product, orderPrice, count);
        product.removeStock(count);
        return orderProduct;
    }

    public void addOrder(Order order) {
        this.order = order;
    }

    public void cancel() { getProduct().addStock(count); }

    public int getTotalPrice() { return getOrderPrice() * getCount(); }

}
