package springjpasideproject.cakebake.domain.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springjpasideproject.cakebake.domain.*;
import springjpasideproject.cakebake.domain.service.BasketService;
import springjpasideproject.cakebake.domain.service.LoginService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/basket/delete")
    public String deleteBasketProduct(HttpServletRequest request,
                                      @RequestParam List<Long> basketProductId,
                                      Model model) {

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
