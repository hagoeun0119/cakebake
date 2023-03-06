package springjpasideproject.cakebake.domain.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindPasswordForm {

    @NotEmpty(message = "아이디는 필수입니다.")
    private String userId;

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

}
