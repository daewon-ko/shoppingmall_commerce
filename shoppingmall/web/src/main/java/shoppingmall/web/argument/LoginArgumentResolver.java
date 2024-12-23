package shoppingmall.web.argument;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shoppingmall.web.filter.session.SessionConst;

@Slf4j
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("LoginArgumentResolver supportsParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);

        boolean hasStringType = String.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasStringType;

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
            // login 페이지로 redirection 요구
        }

        Object sessionObj = session.getAttribute(SessionConst.LOGIN_USER);
        if (sessionObj == null) {
            throw new IllegalStateException("로그인 된 사용자가 아닙니다.");
            // 로그인된 페이지로 Redirection
        }

        return sessionObj;


    }


}
