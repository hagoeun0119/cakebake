package springjpasideproject.cakebake.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpasideproject.cakebake.domain.Basket;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketRepository {

    private final EntityManager em;

    public void save(Basket basket) { em.persist(basket); }

    public Basket findOne(Long id) { return em.find(Basket.class, id); }

    public List<Basket> findByUserPrimaryId(Long userPrimaryId) {
        return em.createQuery("select b from Basket b where b.member.id = :userPrimaryId", Basket.class)
                .setParameter("userPrimaryId", userPrimaryId)
                .getResultList();
    }

}
