package springjpasideproject.cakebake.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springjpasideproject.cakebake.domain.Basket;
import springjpasideproject.cakebake.domain.BasketProduct;
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
        return "/order/basket";
    }
}
