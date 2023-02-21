package springjpasideproject.cakebake.domain.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springjpasideproject.cakebake.domain.Basket;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.SessionConstants;
import springjpasideproject.cakebake.domain.service.BasketService;
import springjpasideproject.cakebake.domain.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final BasketService basketService;

    @GetMapping("/member/join")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/member/join")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Basket basket = new Basket();
        Member member = new Member(basket, form.getUserId(), form.getPassword(), form.getName(), form.getPhone(), form.getEmail());
        memberService.join(member);

        basket.addMemberToBasket(member);
        basketService.createBasket(basket);
        return "redirect:/";
    }

    @GetMapping("/member")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    /**
     * 로그인 기능 구현
     */
    @GetMapping("/member/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }

    @PostMapping("/member/login")
    public String login(@Valid LoginForm form,
                        BindingResult result,
                        HttpServletRequest req) {

        if (result.hasErrors()) {
            return "members/login";
        }

        Member loginMember = memberService.login(form.getUserId(), form.getPassword());

        if (loginMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/login";
        }

        HttpSession session = req.getSession();
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }
}
