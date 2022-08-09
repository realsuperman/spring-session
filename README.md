## 개요
웹 브라우저는 HTTP 요청마다 쿠키 정보를 담아서 HTTP 요청을 합니다 그런 경우 서버쪽에서는 해당 쿠키정보가 유효한 정보인지 판단을 해주어야 합니다 
해당 레포지토리에서는 사용자의 쿠키 정보를 통해 정보를 얻어내고 처리하는 방법에 대해서 학습을 하였습니다

## 기술
java,Spring MVC

## 학습한 내용
1. 쿠키 정보만을 이용하여 로그인을 구현하는 경우 발생하는 문제점
-> 쿠키는 클라이언트에서 변조가 가능하므로 쿠키 정보만을 가지고 로그인을 처리하게 로직을 구현하면 안된다
##
2. 세션을 이용한 로그인 구현
-> 사용자의 요청이 들어오면 세션을 생성 후 해당 세션 정보를 사용자에게 넘겨준다 그리고 사용자는 요청시마다 서버가 넘겨준 세션 정보를
담아서 HTTP 요청을 한다 서버에서는 해당 요청을 보고 올바른 요청인지 판단을 한다
##
3. 세션 타임아웃 관련
-> 기본적으로 스프링에서는 30분 동안 아무런 요청을 안하면 세션을 삭제한다 (해당 시간을 조정 가능)
##
4. 서블릿 필터 관련
-> 기본적으로 [HTTP 요청 -> WAS -> 필터 -> 서블릿(프론트 컨트롤러) -> 스프링 인터셉터 -> 컨트롤러]의 요청 순서로 요청이 된다. 한마디로 사용자의 요청 정보를
컨트롤러가 호출되기 전에 확인을 하고 처리가 필요하다면 처리를 할 수 있다는 것이다. 해당 레포지토리에서는 필터에서 로그인 안된 사용자를 배제 할 수 있는
방법에 대해서 학습을 하였습니다
##
5. 스프링 인터셉트 관련
-> 서블릿 필터와 유사하지만 서블릿 필터보다 더 자세하게 요청 정보들을 컨트롤 할 수 있다. 서블릿 필터와 스프링 인터셉터의 차이점에 대해서 학습을 하였습니다