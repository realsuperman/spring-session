package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter { // 필터는 자바 서블릿의 필터임

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void destroy() {
        log.info("log filter destory");
    }

    @Override // 고객의 요청이 올 때 마다 doFilter가 호출된다
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request; // 다운캐스팅 해야함(getURI같은거는 없음)
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        try{
            log.info("REQUEST [{}][{}]",uuid,requestURI);
            chain.doFilter(request,response); // 다음 할일(프론트 컨트롤러)호출 진행 -> 필터가 더 있으면 그 필터로 넘어감
        }catch(Exception e){
            throw e;
        }finally {
            log.info("RESPONSE [{}][{}]",uuid,requestURI);
        }
    }
}
