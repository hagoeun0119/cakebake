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

    @GetMapping("/basket/delete/{basketId}/{basketProductId}")
    public String deleteBasketProduct(@PathVariable Long basketId, @PathVariable Long basketProductId, Model model){
        basketService.deleteBasketProduct(basketProductId);
        List<BasketProduct> basketProducts = basketService.findOne(basketId).getBasketProducts();
        model.addAttribute("basketProducts", basketProducts);
        return "order/basket";
    }

    @GetMapping("/order/basket")
    public String basket(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "members/login";
        }

        // 수정 필요한 부분
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        model.addAttribute("basketProducts", loginMember);

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
}
