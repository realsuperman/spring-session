package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "login/loginForm";
    }
    //@PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(),form.getPassword());
        if(loginMember==null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";
        }

        // 쿠키의 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 만료)
        // 참고로 이렇게 주려면 HttpServletResponse 필요함
        Cookie idCookie = new Cookie("memberId",String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/";
    }
    //@PostMapping("/login")
    public String login2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(),form.getPassword());
        if(loginMember==null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";
        }

        sessionManager.createSession(loginMember,response);
        return "redirect:/";
    }
    @PostMapping("/login")
    public String login3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(),form.getPassword());
        if(loginMember==null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";
        }

        // 서블릿에서 제공하는 세션을 사용하는 예제임
        HttpSession session = request.getSession(); // 세션이 있으면 있는 세션 반환 없으면 신규 세션 생성
        // getSession의 값을 true로 주면(기본값) 세션이 있으면 기존 세션 반환 없으면 신규 세션 생성
        // getSession의 값을 false로 주면 세션이 있으면 기존 세션을 반환하고 세션이 없으면 새로운 세션 생성안하고 NULL로 반환
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
        return "redirect:/";
    }
    //@PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response,"memberId");
        return "redirect:/";
    }
    //@PostMapping("/logout")
    public String logout2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";
    }
    @PostMapping("/logout")
    public String logout3(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null) session.invalidate(); // map에서 remove하는 느낌
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response,String cookieName){
        Cookie cookie = new Cookie(cookieName,null); // 해당 쿠키를 찾고
        cookie.setMaxAge(0); // 해당 쿠키의 시간을 0으로 지정
        response.addCookie(cookie); // 그리고 쿠키를 서버에서 넘겨주면 웹 브라우저는 쿠키 만료를 인식
    }
}
//getSession은 두가지 일을 수행하는 것 같음 -> 세션 생성 or 세션 찾기