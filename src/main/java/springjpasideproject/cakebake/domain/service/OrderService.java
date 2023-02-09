package springjpasideproject.cakebake.domain.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.*;
import springjpasideproject.cakebake.domain.repository.MemberRepository;
import springjpasideproject.cakebake.domain.repository.OrderProductRepository;
import springjpasideproject.cakebake.domain.repository.OrderRepository;
import springjpasideproject.cakebake.domain.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Long orderFromDetail(Long memberId, Long orderProductId, String receiver, String phone, String email, String comment, String basicAddress, String restAddress, String zipcode) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        OrderProduct orderProduct = orderProductRepository.findOne(orderProductId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        Address address = new Address(basicAddress, restAddress, zipcode);
        delivery.setAddress(address);
        delivery.setStatus(DeliveryStatus.READY);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, receiver, phone, email, comment, orderProduct);

        orderProduct.getOrder().setId(order.getId());

        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public Long order(Long memberId, Long productId, int count, String receiver, String phone, String email, String comment) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Product product = productRepository.findOne(productId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);

        // 주문 상품 생성
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, receiver, phone, email, comment, orderProduct);

        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /**
     * 검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAllByString(orderSearch);
//    }

}
