package springjpasideproject.cakebake.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.service.MemberService;
import springjpasideproject.cakebake.domain.service.OrderService;
import springjpasideproject.cakebake.domain.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Product> products = productService.findProducts();

        model.addAttribute("members", members);
        model.addAttribute("products", products);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String Order(@RequestParam("memberId") Long memberId,
                        @RequestParam("productId") Long productId,
                        @RequestParam("count") int count,
                        @RequestParam("receiver") String receiver,
                        @RequestParam("phone") String phone,
                        @RequestParam("email") String email,
                        @RequestParam("comment") String comment
                        ) {

        orderService.order(memberId, productId, count, receiver, phone, email, comment);
        return "redirect:/orders";
    }
}
