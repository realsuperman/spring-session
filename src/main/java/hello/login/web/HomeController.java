package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentResolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //@GetMapping("/")
    public String home() {
        return "home";
    }
    //@GetMapping("/")
    public String homeLogin(@CookieValue(name="memberId",required = false) Long memberId, Model model){
        if(memberId == null){ // 쿠키가 없다면
            return "home";
        }

        // 로그인 성공
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember==null){
            return "home";
        }
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
    //@GetMapping("/")
    public String homeLogin2(HttpServletRequest request,Model model){
        Member member = (Member)sessionManager.getSession(request);

        // 로그인 성공
        if(member==null) return "home";

        model.addAttribute("member",member);
        return "loginHome";
    }
    //@GetMapping("/")
    public String homeLogin3(HttpServletRequest request,Model model){
        HttpSession session = request.getSession(false);
        if(session==null) return "home";

        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("member",member);
        return "loginHome";
    }
    //@GetMapping("/")
    public String homeLogin4(@SessionAttribute(name=SessionConst.LOGIN_MEMBER,required = false) Member member, HttpServletRequest request, Model model){
        // 넘어오는 쿠키 값에서 세션값을 찾고 있으면 Member객체에 알아서 넣어줌
        if(member==null) return "home";
        model.addAttribute("member",member);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLogin5(@Login Member loginMember,Model model){
        // 넘어오는 쿠키 값에서 세션값을 찾고 있으면 Member객체에 알아서 넣어줌
        if(loginMember==null) return "home";
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
}