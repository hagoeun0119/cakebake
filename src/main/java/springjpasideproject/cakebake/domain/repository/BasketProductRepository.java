package springjpasideproject.cakebake.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpasideproject.cakebake.domain.BasketProduct;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketProductRepository {

    private final EntityManager em;

    public void save(BasketProduct basketProduct) { em.persist(basketProduct); }

    public BasketProduct findOne(Long id) { return em.find(BasketProduct.class, id); }

    public List<BasketProduct> findAll(Long basketId) {
        return em.createQuery("select b from BasketProduct b where b.basket.id = :basketId", BasketProduct.class)
                .setParameter("basketId", basketId)
                .getResultList();
    }

    public void delete(BasketProduct basketProduct) { em.remove(basketProduct); }
}
