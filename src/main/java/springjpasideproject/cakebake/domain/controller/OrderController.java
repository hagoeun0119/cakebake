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
import springjpasideproject.cakebake.domain.controller.form.OrderForm;
import springjpasideproject.cakebake.domain.controller.form.OrderProductForm;
import springjpasideproject.cakebake.domain.service.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final BasketService basketService;

    @GetMapping("/order")
    public String order(HttpServletRequest request,
                        Model model) {

        if (LoginService.loginCheck(request, model)) return "members/login";

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        List<Order> orders = orderService.findOrdersByUserId(loginMember.getId());
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @GetMapping("/order/detail/orderForm")
    public String orderCreateForm(@RequestParam Long productId,
                                  @RequestParam int productCount,
                                  HttpServletRequest request,
                                  Model model) {

        if (LoginService.loginCheck(request, model)) return "members/login";

        OrderForm orderForm = new OrderForm();
        Product product = productService.findOne(productId);
        orderForm.getProductAndCountList().put(product, productCount);
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

    @GetMapping("/order/basket")
    public String basket(Model model, HttpServletRequest request) {

        if (LoginService.loginCheck(request, model)) return "members/login";

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        List<BasketProduct> basketProducts = basketService.findAllBasketProduct(loginMember.getBasket().getId());
        model.addAttribute("basketProducts", basketProducts);

        return "order/basket";
    }

    @PostMapping("/order/basket")
    public String addProductToBasket(@RequestParam Long productId, OrderProductForm form, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        List<BasketProduct> basketProducts = basketService.createBasketProduct(productId, loginMember.getId(), form.getCount());
        model.addAttribute("basketProducts", basketProducts);
        return "order/basket";
    }

    @GetMapping("/order/basket/orderForm")
    public String orderCreateForm(@RequestParam List<Long> basketProductIdList,
                                  HttpServletRequest request,
                                  Model model) {

        if (LoginService.loginCheck(request, model)) return "members/login";

        OrderForm orderForm = new OrderForm();

        for (Long basketProductId : basketProductIdList) {
            BasketProduct basketProduct = basketService.findOneBasketProduct(basketProductId);
            orderForm.getProductAndCountList().put(basketProduct.getProduct(), basketProduct.getCount());
            basketService.deleteBasketProduct(basketProductId);
        }

        model.addAttribute("orderForm", orderForm);
        return "order/orderCreateForm";
    }

    @PostMapping("/order/basket/orderForm")
    public String orderCreateForm(@RequestParam("basketProductId") List<Long> basketProductIdList,
                                  RedirectAttributes redirect) {

        redirect.addAttribute("basketProductIdList", basketProductIdList);
        return "redirect:/order/basket/orderForm";
    }
}

