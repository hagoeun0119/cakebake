package springjpasideproject.cakebake.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpasideproject.cakebake.domain.OrderProduct;

@Repository
@RequiredArgsConstructor
public class OrderProductRepository {

    private final EntityManager em;

    public void save(OrderProduct orderProduct) { em.persist(orderProduct); }

    public OrderProduct findOne(Long id) { return em.find(OrderProduct.class, id); }
}
