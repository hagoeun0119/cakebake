package springjpasideproject.cakebake.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.repository.ProductRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void updateItem(Long itemId, String name, String ingredient, String image, int price, int stockQuantity) {
        Product product = productRepository.findOne(itemId);
        product.setName(name);
        product.setIngredient(ingredient);
        product.setImage(image);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
    }

    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    public Product findOne(Long productId) {
        return productRepository.findOne(productId);
    }
}
