package springjpasideproject.cakebake.domain.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductForm {

    private Long id;
    private String name;
    private String ingredient;
    private String image;
    private int price;
    private int stockQuantity;

    private String category;
}
