package springjpasideproject.cakebake.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Category;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.repository.CategoryRepository;
import springjpasideproject.cakebake.domain.repository.ProductRepository;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product registerProduct(String name, String ingredient, String image, int price, int stockQuantity, Category category) {
        Product product = Product.builder()
                .name(name)
                .ingredient(ingredient)
                .image(image)
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category).build();
        productRepository.save(product);
        return product;
    }

    @Transactional
    public void updateProduct(Long itemId, String name, String ingredient, String image, int price, int stockQuantity, String categoryName) {
        Product product = productRepository.findOne(itemId);
        List<Category> categoryList = categoryRepository.findByName(categoryName);
        Iterator<Product> products = categoryList.get(0).getProducts().listIterator();

        while(products.hasNext()) {
            Product findProduct = products.next();
            if (findProduct.getId().equals(itemId)) {
                products.remove();
            }
        }

        product.updateProduct(name, ingredient, image, price, stockQuantity, categoryList.get(0));
        categoryList.get(0).getProducts().add(product);
    }

    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    public List<Product> findProductsByCategory(String category) { return productRepository.findProductsByCategory(category); }

    public Product findOne(Long productId) { return productRepository.findOne(productId);}

}
