package springjpasideproject.cakebake.domain.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.SessionConstants;
import springjpasideproject.cakebake.domain.controller.LoginForm;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

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
}
