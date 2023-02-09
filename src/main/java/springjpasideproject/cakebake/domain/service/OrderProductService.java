package springjpasideproject.cakebake.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.OrderProduct;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.repository.OrderProductRepository;
import springjpasideproject.cakebake.domain.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderProductService {

    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;


    public OrderProduct findOne(Long orderProductId) { return orderProductRepository.findOne(orderProductId); }

    /**
     * 단일 상품 주문
     */
    @Transactional
    public Long orderProductFromDetail(Long productId, int count) {

        Product product = productRepository.findOne(productId);
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), count);

        orderProductRepository.save(orderProduct);
        return orderProduct.getId();

    }
}
