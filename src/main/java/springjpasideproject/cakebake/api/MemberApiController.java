package springjpasideproject.cakebake.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springjpasideproject.cakebake.domain.Basket;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.SessionConstants;
import springjpasideproject.cakebake.domain.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

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

    @PutMapping("/api/v1/member")
    public UpdateMemberResponse updateMemberV1(@RequestBody @Valid UpdateMemberRequest request,
                                               HttpServletRequest servletRequest) {

        HttpSession session = servletRequest.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        memberService.update(loginMember, request.name, request.phone, request.email);
        return new UpdateMemberResponse(loginMember.getId(), loginMember.getName(), loginMember.getPhone(), loginMember.getEmail());
    }

    @GetMapping("/api/v1/member")
    public Result memberV1() {

        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
        private String phone;
        private String email;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
        private String phone;
        private String email;
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
