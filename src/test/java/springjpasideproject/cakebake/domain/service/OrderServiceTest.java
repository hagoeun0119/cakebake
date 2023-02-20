package springjpasideproject.cakebake.domain.service;


import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.*;
import springjpasideproject.cakebake.domain.repository.OrderRepository;
import springjpasideproject.cakebake.exception.NotEnoughStockException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {

        //given
        Member member = createMember();
        Product product = createProduct("블루베리 케이크", "블루베리", "url", 20000, 10, "케이크");

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), product.getId(), orderCount, "Kim", "010-1234-5678", "hi@naver.com", "빠른 배송");

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderProducts().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 20000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, product.getStockQuantity());

}

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {

        //given
        Member member = createMember();
        Product product = createProduct("블루베리 케이크", "블루베리", "url", 20000, 10, "케이크");

        int orderCount = 11;

        //when
        orderService.order(member.getId(), product.getId(), orderCount, "Kim", "010-1234-5678", "hi@naver.com", "빠른 배송");

        //then
        fail("재고 수량 부족 예외가 발행해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {

        //given
        Member member = createMember();
        Product product = createProduct("블루베리 케이크", "블루베리", "url", 20000, 10, "케이크");

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), product.getId(), orderCount, "Kim", "010-1234-5678", "hi@naver.com", "빠른 배송");

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소시 상태는 CANCEL이다.", 10, product.getStockQuantity());
    }

    private Product createProduct(String name, String ingredient, String image, int price, int stockQuantity, String categoryName) {
        Category category = new Category(categoryName);
        Product product = Product.builder()
                .name(name)
                .ingredient(ingredient)
                .image(image)
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category).build();
        em.persist(product);
        return product;
    }

    private Member createMember() {
        Basket basket = new Basket();
        Member member = new Member(basket, "hi1234", "Kim", "0123", "010-0000-0000", "kim@naver.com");
        em.persist(member);
        return member;
    }

}
