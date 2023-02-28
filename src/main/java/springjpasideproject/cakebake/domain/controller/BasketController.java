package springjpasideproject.cakebake.domain.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springjpasideproject.cakebake.domain.BasketProduct;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.SessionConstants;
import springjpasideproject.cakebake.domain.service.BasketService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/basket/delete")
    public String deleteBasketProduct(HttpServletRequest request,
                                      @RequestParam List<Long> basketProductId,
                                      Model model){

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        for (Long basketProduct : basketProductId) {
            basketService.deleteBasketProduct(basketProduct);
        }

        List<BasketProduct> basketProducts = basketService.findOne(loginMember.getBasket().getId()).getBasketProducts();
        model.addAttribute("basketProducts", basketProducts);

        return "redirect:/order/basket";
    }

    @GetMapping("/order/basket")
    public String basket(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            model.addAttribute("loginForm", new LoginForm());
            return "members/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        if (loginMember == null) {
            model.addAttribute("loginForm", new LoginForm());
            return "members/login";
        }

        List<BasketProduct> basketProducts = basketService.findAllBasketProduct(loginMember.getBasket().getId());
        model.addAttribute("basketProducts", basketProducts);

        return "order/basket";
    }

    @PostMapping( "/order/basket")
    public String addProductToBasket(@RequestParam Long productId, OrderProductForm form, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        List<BasketProduct> basketProducts = basketService.createBasketProduct(productId, loginMember.getId(), form.getCount());
        model.addAttribute("basketProducts", basketProducts);
        return "order/basket";
    }

    @PostMapping("/order/basket/orderForm")
    public String orderCreateForm(@RequestParam("productId") List<Long> basketProductId, Model model) {

        model.addAttribute("orderForm", new OrderForm());
        model.addAttribute("orderProducts", basketProductId);

        return "/order/orderCreateForm";
    }
}
