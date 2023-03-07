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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springjpasideproject.cakebake.domain.Basket;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.SessionConstants;
import springjpasideproject.cakebake.domain.controller.form.*;
import springjpasideproject.cakebake.domain.service.BasketService;
import springjpasideproject.cakebake.domain.service.LoginService;
import springjpasideproject.cakebake.domain.service.MemberService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final BasketService basketService;
    private final LoginService loginService;

    @GetMapping("/member/join")
    public String createMemberForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/member/join")
    public String createMember(@Valid MemberForm memberForm, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Basket basket = new Basket();
        Member member = new Member(basket, memberForm.getUserId(), memberForm.getPassword(), memberForm.getName(), memberForm.getPhone(), memberForm.getEmail());
        memberService.join(member);

        basket.addMemberToBasket(member);
        basketService.createBasket(basket);
        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }

    @PostMapping("/member/login")
    public String login(@Valid LoginForm loginForm,
                        BindingResult result,
                        HttpServletRequest req,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        Model model) {

        if (result.hasErrors()) {
            return "members/login";
        }

        Member loginMember = loginService.login(loginForm.getUserId(), loginForm.getPassword());

        if (loginMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            model.addAttribute("loginFailMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "members/login";
        }

        HttpSession session = req.getSession();
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/member/id/find")
    public String findId(Model model) {
        model.addAttribute("findIdForm", new FindIdForm());
        return "members/findId";
    }

    @PostMapping("/member/id/find")
    public String findId(@Valid FindIdForm findIdForm,
                         BindingResult result,
                         RedirectAttributes redirect,
                         Model model) {

        Member member = loginService.findId(findIdForm.getName(), findIdForm.getEmail());

        if (member == null) {
            result.reject("findIdFail", "가입 된 회원이 존재하지 않습니다.");
            model.addAttribute("findIdFailMessage", "가입 된 회원이 존재하지 않습니다.");
            model.addAttribute("loginForm", new LoginForm());
            return "members/login";
        }

        redirect.addAttribute("findId", member.getUserId());
        return "redirect:/member/id/find/show";
    }

    @GetMapping("/member/id/find/show")
    public String showId(@RequestParam String findId,
                         Model model) {
        model.addAttribute("findId", findId);
        return "members/showFindId";
    }

    @GetMapping("/member/password/find")
    public String findPassword(Model model) {
        model.addAttribute("findPasswordForm", new FindPasswordForm());
        return "members/findPassword";
    }

    @PostMapping("/member/password/find")
    public String changePasswordInfo(@Valid FindPasswordForm findPasswordForm,
                                     RedirectAttributes redirect,
                                     BindingResult result,
                                     Model model) {

        if (result.hasErrors()) {
            return "members/findPassword";
        }

        if (loginService.findByUserIdAndName(findPasswordForm.getUserId(), findPasswordForm.getName()) == null) {
            result.reject("findPasswordFail", "가입 된 회원이 존재하지 않습니다.");
            model.addAttribute("findPasswordFailMessage", "가입 된 회원이 존재하지 않습니다.");
            model.addAttribute("loginForm", new LoginForm());
            return "/members/login";
        }

        redirect.addAttribute("userId", findPasswordForm.getUserId());
        redirect.addAttribute("name", findPasswordForm.getName());
        return "redirect:/member/password/change";
    }

    @GetMapping("/member/password/change")
    public String changePasswordForm(@RequestParam String userId,
                                     @RequestParam String name,
                                     Model model) {
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setUserId(userId);
        changePasswordForm.setName(name);
        model.addAttribute("changePasswordForm", changePasswordForm);
        return "members/changePassword";
    }

    @PostMapping("/member/password/change")
    public String changePassword(@RequestParam String userId,
                                 @RequestParam String name,
                                 @Valid ChangePasswordForm changePasswordForm,
                                 BindingResult result,
                                 Model model) {

        if (result.hasErrors()) {
            return "members/changePassword";
        }

        if (!changePasswordForm.getPassword().equals(changePasswordForm.getCheckPassword())) {
            model.addAttribute("changePasswordFailMessage", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("changePasswordFailForm", new ChangePasswordForm());
            return "members/changePassword";
        }

        loginService.changePassword(userId, name, changePasswordForm.getPassword());
        model.addAttribute("changePasswordMessage", "비밀번호를 변경하였습니다.");
        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }
}
