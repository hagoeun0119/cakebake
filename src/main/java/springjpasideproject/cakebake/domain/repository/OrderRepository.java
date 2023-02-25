package springjpasideproject.cakebake.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.Order;
import springjpasideproject.cakebake.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) { em.persist(order); }

    public Order findOne(Long id) { return em.find(Order.class, id); }

    public List<Order> findByUserId(Long userId) {
        String findQuery = "select o from Order as o WHERE o.member.id = :userId";
        return em.createQuery(findQuery, Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
