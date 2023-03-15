package springjpasideproject.cakebake.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springjpasideproject.cakebake.domain.Basket;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/member")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid CreateMemberRequest request) {

        Basket basket = new Basket();
        Member member = Member.builder()
                .basket(basket)
                .userId(request.getUserId())
                .password(request.getPassword())
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class  CreateMemberRequest {

        @NotEmpty
        private String userId;

        @NotEmpty
        private String password;

        @NotEmpty
        private String name;

        @NotEmpty
        private String phone;

        @NotEmpty
        private String email;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
