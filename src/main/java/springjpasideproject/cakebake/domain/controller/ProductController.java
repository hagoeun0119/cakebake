package springjpasideproject.cakebake.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springjpasideproject.cakebake.domain.Category;
import springjpasideproject.cakebake.domain.Product;
import springjpasideproject.cakebake.domain.controller.form.OrderProductForm;
import springjpasideproject.cakebake.domain.controller.form.ProductForm;
import springjpasideproject.cakebake.domain.service.CategoryService;
import springjpasideproject.cakebake.domain.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products/new")
    public String registerProductForm(Model model) {
        model.addAttribute("form", new ProductForm());
        return "products/createProductForm";
    }

    @PostMapping("/products/new")
    public String registerProduct(ProductForm form) {
        Category category = categoryService.createCategory(form.getCategory());
        Product product = productService.registerProduct(form.getName(), form.getIngredient(), form.getImage(), form.getPrice(), form.getStockQuantity(), category);
        category.getProducts().add(product);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String list(Model model) {
        List<Product> products = productService.findProducts();
        model.addAttribute("products", products);
        return "products/productList";
    }

    @GetMapping("/category/{category}")
    public String listByCategory(@PathVariable("category") String category, Model model) {
        List<Product> products = productService.findProductsByCategory(category);
        model.addAttribute("products", products);
        return "category/productListByCategory";
    }

    @GetMapping("/product/{name}/{productId}/category/{category}/{categoryId}")
    public String productDetailForm(@PathVariable("name") String name,
                                    @PathVariable("productId") Long productId,
                                    @PathVariable("category") String categoryName,
                                    @PathVariable("categoryId") Long categoryId,
                                    Model model) {

        Product product = productService.findOne(productId);

        model.addAttribute("product", product);
        model.addAttribute("orderProductForm", new OrderProductForm());
        return "products/productDetailForm";
    }

    @GetMapping("/products/{productId}/edit")
    public String updateProductForm(@PathVariable("productId") Long productId, Model model) {

        Product changeProduct = (Product) productService.findOne(productId);

        ProductForm form = new ProductForm();
        form.setName(changeProduct.getName());
        form.setIngredient(changeProduct.getIngredient());
        form.setImage(changeProduct.getImage());
        form.setPrice(changeProduct.getPrice());
        form.setStockQuantity(changeProduct.getStockQuantity());
        form.setCategory(changeProduct.getCategory().getName());

        model.addAttribute("form", form);
        return "products/updateProductForm";
    }

    @PostMapping("/products/{productId}/edit")
    public String updateProductForm(@PathVariable Long productId, @ModelAttribute("form") ProductForm form) {
        productService.updateProduct(productId, form.getName(), form.getIngredient(), form.getImage(), form.getPrice(), form.getStockQuantity(), form.getCategory());
        return "redirect:/products";
    }
}
