package springjpasideproject.cakebake.domain.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springjpasideproject.cakebake.domain.*;
import springjpasideproject.cakebake.domain.service.BasketService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/basket/delete")
    public String deleteBasketProduct(HttpServletRequest request, @RequestParam List<Long> basketProductId, Model model) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        for (Long basketProduct : basketProductId) {
            basketService.deleteBasketProduct(basketProduct);
        }

        List<BasketProduct> basketProducts = basketService.findOne(loginMember.getBasket().getId()).getBasketProducts();
        model.addAttribute("basketProducts", basketProducts);
        return "redirect:/order/basket";
    }
}
