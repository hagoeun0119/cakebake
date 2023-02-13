package springjpasideproject.cakebake.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpasideproject.cakebake.domain.Basket;
import springjpasideproject.cakebake.domain.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketRepository {

    private final EntityManager em;

    public void save(Basket basket) { em.persist(basket); }

    public Basket findOne(Long id) { return em.find(Basket.class, id); }

    public List<Basket> findByUserId(Long userId) {
        return em.createQuery("select m from Member m where m.userId = :userId", Basket.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
