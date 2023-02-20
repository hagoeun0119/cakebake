package springjpasideproject.cakebake.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Basket;
import springjpasideproject.cakebake.domain.BasketProduct;
import springjpasideproject.cakebake.domain.Category;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.repository.BasketProductRepository;
import springjpasideproject.cakebake.domain.repository.BasketRepository;
import springjpasideproject.cakebake.domain.repository.ProductRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketProductRepository basketProductRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Basket findOne(Long basketId) { return basketRepository.findOne(basketId); }

    @Transactional
    public void createBasket(Basket basket) {

        // 중복되는 내역이 있는지 확인
        basketRepository.save(basket);

    }

    @Transactional
    public List<BasketProduct> createBasketProduct(Long productId, Long userPrimaryId, int count) {

        Product product = productRepository.findOne(productId);
        Basket basket = basketRepository.findByUserPrimaryId(userPrimaryId).get(0);
        BasketProduct basketProduct = new BasketProduct(basket, product, count);
        basket.getBasketProducts().add(basketProduct);
        basketProductRepository.save(basketProduct);

        return basket.getBasketProducts();
    }

    public void deleteBasketProduct(Long basketProductId) {

        BasketProduct basketProduct = basketProductRepository.findOne(basketProductId);
        basketProductRepository.delete(basketProduct);
    }
}
