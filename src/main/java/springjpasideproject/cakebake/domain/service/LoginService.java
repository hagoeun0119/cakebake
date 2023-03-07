package springjpasideproject.cakebake.domain.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.SessionConstants;
import springjpasideproject.cakebake.domain.controller.form.LoginForm;
import springjpasideproject.cakebake.domain.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public static boolean loginCheck(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            model.addAttribute("loginForm", new LoginForm());
            return true;
        }

        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        if (loginMember == null) {
            model.addAttribute("loginForm", new LoginForm());
            return true;
        }
        return false;
    }

    public Member login(String userId, String password) {
        return memberRepository.findByUserId(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);

    }

    public Member findId(String name, String email) {
        return memberRepository.findByName(name)
                .filter(m -> m.getEmail().equals(email))
                .orElse(null);
    }

    public Member findByUserIdAndName(String userId, String name) {
        return memberRepository.findByUserId(userId)
                .filter(m -> m.getName().equals(name))
                .stream().findFirst()
                .orElse(null);
    }

    @Transactional
    public void changePassword(String userId, String name, String password) {
        Member member = findByUserIdAndName(userId, name);
        member.changePassword(password);
    }
}
