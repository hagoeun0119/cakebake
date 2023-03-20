package springjpasideproject.cakebake.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springjpasideproject.cakebake.domain.repository.order.OrderQueryDto;
import springjpasideproject.cakebake.domain.repository.order.OrderQueryRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/order")
    public List<OrderQueryDto> ordersV1() {
        return orderQueryRepository.findAllOrderDto();
    }
}
