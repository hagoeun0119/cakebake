package springjpasideproject.cakebake.domain.controller;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderProductForm {

    @NotEmpty(message = "수량을 입력해주세요.")
    private int count;

}