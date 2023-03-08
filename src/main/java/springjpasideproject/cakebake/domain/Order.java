package springjpasideproject.cakebake.domain;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name =  "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private String receiver;

    private String phone;

    private String email;

    private String comment;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.addOrder(this);
    }

    public static Order createOrder(Member member, Delivery delivery, String receiver, String phone, String email, String comment) {
        Order order = new Order();
        order.member = member;
        order.delivery = delivery;
        order.receiver = receiver;
        order.phone = phone;
        order.email = email;
        order.comment = comment;
        order.orderDate = LocalDateTime.now();
        order.status = OrderStatus.ORDER;
        return order;
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.status = OrderStatus.CANCEL;
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderProduct orderProduct : orderProducts) {
            totalPrice += orderProduct.getTotalPrice();
        }
        return totalPrice;
    }
}
