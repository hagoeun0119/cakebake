package springjpasideproject.cakebake.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpasideproject.cakebake.domain.Product;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager em;

    public void save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
        } else {
            em.merge(product);
        }
    }

    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }

    /**
     * find product by category
     */
    public List<Product> findByCategory(String category) {
        String findQuery = "select p from Product as p INNER JOIN p.category c WHERE c.name = :category";
        return em.createQuery(findQuery, Product.class)
                .setParameter("category", category)
                .getResultList();
    }

    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class)
                .getResultList();
    }
}
