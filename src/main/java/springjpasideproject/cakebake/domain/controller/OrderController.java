package springjpasideproject.cakebake.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.service.MemberService;
import springjpasideproject.cakebake.domain.service.OrderProductService;
import springjpasideproject.cakebake.domain.service.OrderService;
import springjpasideproject.cakebake.domain.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ProductService productService;
    private final OrderProductService orderProductService;

    @GetMapping("/product/{name}/{productId}/category/{category}/{categoryId}")
    public String productDetailForm(@PathVariable("name") String name,
                                    @PathVariable("productId") Long productId,
                                    @PathVariable("category") String categoryName,
                                    @PathVariable("categoryId") Long categoryId,
                                    Model model) {

        Product productDetail = productService.findOne(productId);

        model.addAttribute("product", productDetail);
        model.addAttribute("orderProductForm", new OrderProductForm());

        return "products/productDetailForm";
    }

    @PostMapping("/order/orderForm/{productId}")
    public String productOrderFromDetail(@PathVariable("productId") Long productId, OrderProductForm form, RedirectAttributes redirect) {

        Long orderProductId = orderProductService.orderProductFromDetail(productId, form.getCount());
        redirect.addAttribute("orderProductId", orderProductId);

        return "redirect:/order/orderForm";
    }

    @GetMapping("/order/orderForm")
    public String orderCreateForm(@RequestParam("orderProductId") Long orderProductId, Model model) {

        // 현재 member를 list로 구현했음 -> 수정사항
        List<Member> members = memberService.findMembers();

        model.addAttribute("members", members);
        model.addAttribute("orderForm", new OrderForm());
        model.addAttribute("orderProductId", orderProductId);

        return "order/orderCreateForm";
    }

    @PostMapping("/order/submit/{orderProductId}")
    public String orderCreate(@RequestParam Long memberId, @PathVariable Long orderProductId, @Valid OrderForm form) {

        orderService.orderFromDetail(memberId, orderProductId, form.getReceiver(), form.getPhone(), form.getEmail(), form.getComment(), form.getBasicAddress(), form.getRestAddress(), form.getZipcode());

        return "redirect:/";
    }
}

