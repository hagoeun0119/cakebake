package springjpasideproject.cakebake.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ProductForm());
        return "products/createProductForm";
    }

    @PostMapping("/products/new")
    public String create(ProductForm form) {
        Product product = new Product();
        product.setName(form.getName());
        product.setIngredient(form.getIngredient());
        product.setImage(form.getImage());
        product.setPrice(form.getPrice());
        product.setStockQuantity(form.getStockQuantity());
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String list(Model model) {
        List<Product> products = productService.findProducts();
        model.addAttribute("products", products);
        return "products/productList";
    }

    @GetMapping("products/{productId}/edit")
    public String updateProductForm(@PathVariable("productId") Long productId, Model model) {
        Product changeProduct = (Product) productService.findOne(productId);
        ProductForm form = new ProductForm();
        form.setName(changeProduct.getName());
        form.setIngredient(changeProduct.getIngredient());
        form.setImage(changeProduct.getImage());
        form.setPrice(changeProduct.getPrice());
        form.setStockQuantity(changeProduct.getStockQuantity());
        model.addAttribute("form", form);
        return "products/updateProductForm";
    }

    @PostMapping("products/{productId}/edit")
    public String updateProductForm(@PathVariable Long productId, @ModelAttribute("form") ProductForm form) {
        productService.updateItem(productId, form.getName(), form.getIngredient(), form.getImage(), form.getPrice(), form.getStockQuantity());
        return "redirect:/products";
    }

}
