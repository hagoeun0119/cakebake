package springjpasideproject.cakebake.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.*;
import springjpasideproject.cakebake.domain.repository.OrderRepository;
import springjpasideproject.cakebake.domain.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long order(Member member, List<Long> productIdList, List<Integer> productCountList, String receiver, String phone, String email, String comment, String basicAddress, String restAddress, String zipcode) {

        Address address = new Address(basicAddress, restAddress, zipcode);
        final Delivery delivery = Delivery.builder()
                .address(address)
                .status(DeliveryStatus.READY)
                .build();

        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .receiver(receiver)
                .phone(phone)
                .email(email)
                .comment(comment)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.ORDER)
                .build();

        delivery.addOrderToDelivery(order);

        for (int index = 0; index < productIdList.size(); index++) {

            Product product = productRepository.findOne(productIdList.get(index));
            OrderProduct orderProduct = OrderProduct.builder()
                    .product(product)
                    .order(order)
                    .orderPrice(product.getPrice())
                    .count(productCountList.get(index))
                    .build();

            product.removeStock(productCountList.get(index));
            order.getOrderProducts().add(orderProduct);

        }

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

}
