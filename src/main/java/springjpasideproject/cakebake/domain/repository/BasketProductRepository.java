package springjpasideproject.cakebake.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpasideproject.cakebake.domain.BasketProduct;

@Repository
@RequiredArgsConstructor
public class BasketProductRepository {

    private final EntityManager em;

    public void save(BasketProduct basketProduct) { em.persist(basketProduct); }

    public BasketProduct findOne(Long id) { return em.find(BasketProduct.class, id); }
}
