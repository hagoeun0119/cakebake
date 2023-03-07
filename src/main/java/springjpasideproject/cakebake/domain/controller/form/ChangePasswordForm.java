package springjpasideproject.cakebake.domain.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChangePasswordForm {

    private String userId;

    private String name;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    @NotEmpty(message = "다시 입력해주세요")
    private String checkPassword;
}
