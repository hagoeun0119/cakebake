package springjpasideproject.cakebake.domain.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderForm {

    @NotEmpty(message = "받으시는 분을 입력해주세요.")
    private String receiver;

    @NotEmpty(message = "전화번호를 입력해주세요.")
    private String phone;

    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    private String comment;

    @NotEmpty(message = "기본 주소를 입력해주세요.")
    private String basicAddress;

    private String restAddress;

    @NotEmpty(message = "우편번호를 입력해주세요.")
    private String zipcode;



}
