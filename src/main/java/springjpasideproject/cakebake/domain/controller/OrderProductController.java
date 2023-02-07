package springjpasideproject.cakebake.domain.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.service.OrderService;
import springjpasideproject.cakebake.domain.service.ProductService;


@Controller
@AllArgsConstructor
public class OrderProductController {

    private final ProductService productService;
    private final OrderService orderService;

    /**
     * 제품 상세 페이지
     */
    @GetMapping("/product/{name}/{productId}/category/{category}/{categoryId}")
    public String productDetailForm(@PathVariable("name") String name, @PathVariable("productId") Long productId, @PathVariable("category") String categoryName, @PathVariable("categoryId") Long categoryId, Model model) {

        Product productDetail = (Product) productService.findOne(productId);

        model.addAttribute("product", productDetail);
        model.addAttribute("form", new OrderProductForm());

        return "products/productDetailForm";
    }

    @PostMapping("/product/{name}/{productId}/category/{category}/{categoryId}")
    public String productDetail(@PathVariable String name, @PathVariable Long productId, @PathVariable String categoryName, @PathVariable String category, @PathVariable Long categoryId, @ModelAttribute("form") OrderProductForm form) {

        orderService.orderSingleProduct(productId, form.getCount());

        // 이후로 더 구현
        return "redirect:/";
    }
}
