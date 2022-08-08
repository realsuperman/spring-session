package hello.login;

import hello.login.web.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration // 수동 빈 등록
public class WebConfig {
    @Bean
    public FilterRegistrationBean logFilter(){ // 스프링 부트를 사용하므로 FilterRegistrationBean를 이용해야함
        FilterRegistrationBean<Filter> filterFilterRegistrationBean =  new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter()); // 사용될 필터 설정
        filterFilterRegistrationBean.setOrder(1); // 여러 체인이 있을때 실행되어야 할 체인의 순서를 조정(값이 낮을수록 우선순위 높다)
        filterFilterRegistrationBean.addUrlPatterns("/*"); // 해당 필터를 적용할 url
        return filterFilterRegistrationBean;
    }
}