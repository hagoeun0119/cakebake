package springjpasideproject.cakebake.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/join")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/member/join")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()){
            return "members/createMemberForm";
        }

        Member member = new Member();
        member.setUserId(form.getUserId());
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setPhone(form.getPhone());
        member.setEmail(form.getEmail());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/member")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("member", members);
        return "member/memberList";
    }


}
