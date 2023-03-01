package springjpasideproject.cakebake.domain.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springjpasideproject.cakebake.domain.*;
import springjpasideproject.cakebake.domain.service.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/order")
    public String order(HttpServletRequest request,
                        Model model) {

        HttpSession session = request.getSession();

        if (session != null) {
            Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);

            if (loginMember == null) {
                model.addAttribute("loginForm", new LoginForm());
                return "members/login";
            }

            List<Order> orders = orderService.findOrdersByUserId(loginMember.getId());
            model.addAttribute("orders", orders);
            return "order/orderList";
        }

        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }

    @GetMapping("/product/{name}/{productId}/category/{category}/{categoryId}")
    public String productDetailForm(@PathVariable("name") String name,
                                    @PathVariable("productId") Long productId,
                                    @PathVariable("category") String categoryName,
                                    @PathVariable("categoryId") Long categoryId,
                                    Model model) {

        Product productDetail = productService.findOne(productId);
        List<Member> members = memberService.findMembers();

        model.addAttribute("members", members);
        model.addAttribute("product", productDetail);
        model.addAttribute("orderProductForm", new OrderProductForm());
        return "products/productDetailForm";
    }

    @GetMapping("/order/detail/orderForm")
    public String orderCreateForm(Model model,
                                  @RequestParam Long productId,
                                  @RequestParam int productCount) {

        OrderForm orderForm = new OrderForm();
        orderForm.getProductIdList().add(productId);
        orderForm.getProductCountList().add(productCount);
        model.addAttribute("orderForm", orderForm);
        return "order/orderCreateForm";
    }

    @PostMapping( "/order/detail/orderForm")
    public String productOrderFromDetail(@RequestParam Long productId,
                                         OrderProductForm orderProductForm, RedirectAttributes redirect) {

        redirect.addAttribute("productId", productId);
        redirect.addAttribute("productCount", orderProductForm.getCount());
        return "redirect:/order/detail/orderForm";
    }

    @PostMapping("/order/submit")
    public String orderCreate(HttpServletRequest request,
                              @RequestParam("productId") List<Long> productIdList,
                              @RequestParam("productCount") List<Integer> productCountList,
                              @Valid OrderForm form) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        orderService.order(loginMember, productIdList, productCountList, form.getReceiver(), form.getPhone(), form.getEmail(), form.getComment(), form.getBasicAddress(), form.getRestAddress(), form.getZipcode());
        return "redirect:/";
    }

    @PostMapping("/order/delete/{orderId}")
    public String orderCancel(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order";
    }

}

