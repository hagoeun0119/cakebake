package springjpasideproject.cakebake.domain.controller.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderProductForm {

    @NotNull(message = "수량을 입력해주세요.")
    private int count;

}