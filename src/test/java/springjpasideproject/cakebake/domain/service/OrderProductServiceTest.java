package springjpasideproject.cakebake.domain.service;


import jakarta.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Category;
import springjpasideproject.cakebake.domain.OrderProduct;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.repository.OrderProductRepository;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderProductServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderProductRepository orderProductRepository;
    @Autowired OrderProductService orderProductService;

    @Test
    public void testOrderProductService() throws Exception {

        //given
        Product product = createProduct("블루베리 케이크", "블루베리", "url", 20000, 10, "케이크");
        int count = 2;

        //when
        Long orderProductId = orderProductService.orderProductFromDetail(product.getId(), count);

        //then
        OrderProduct orderProduct = orderProductRepository.findOne(orderProductId);

        assertEquals(orderProduct.getOrderPrice(), 20000);

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
}
