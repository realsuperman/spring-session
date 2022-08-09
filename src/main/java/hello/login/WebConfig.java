package hello.login;

import hello.login.web.argumentResolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LoginCheckInterceptor;
import hello.login.web.interceptor.LoginInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration // 수동 빈 등록
public class WebConfig implements WebMvcConfigurer { // 스프링 인터셉터는 일반적인 빈 등록과 다르므로 WebMvcConfigurer 인터페이스를 구현함

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) { // 아규먼트 리졸버 등록
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // 오버라이드 메소드 필요
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**") // 모든 경로에 지정
                .excludePathPatterns("/css/**","/*.ico","/error"); // 예외 경로 지정
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/","/members/add","/logout","/css/**","/*.ico","/error");
    }

    //@Bean
    public FilterRegistrationBean logFilter(){ // 스프링 부트를 사용하므로 FilterRegistrationBean를 이용해야함
        FilterRegistrationBean<Filter> filterFilterRegistrationBean =  new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter()); // 사용될 필터 설정
        filterFilterRegistrationBean.setOrder(1); // 여러 체인이 있을때 실행되어야 할 체인의 순서를 조정(값이 낮을수록 우선순위 높다)
        filterFilterRegistrationBean.addUrlPatterns("/*"); // 해당 필터를 적용할 url
        return filterFilterRegistrationBean;
    }

    //@Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}