package shoppingmall.web.common.filter.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.AuthErrorCode;
import shoppingmall.domainservice.domain.user.dto.request.LoginUserRequestDto;

import java.io.IOException;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;

    public CustomLoginFilter(AuthenticationManager authenticationManager, final ObjectMapper objectMapper) {
        super.setFilterProcessesUrl("/api/v1/auth/login");
        this.setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginUserRequestDto loginUserRequestDto = objectMapper.readValue(request.getInputStream(), LoginUserRequestDto.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginUserRequestDto.getEmail(), loginUserRequestDto.getPassword());

            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new ApiException(AuthErrorCode.INVALID_LOGIN_REQUEST);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);
//        request.getSession().setAttribute(SessionConst.LOGIN_USER, authResult.getPrincipal());

//        ((UsernamePasswordAuthenticationToken) authResult).setAuthenticated(true);


        // SecurityContext에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authResult);

        // 세션에도 저장 (Spring Security가 자동으로 처리)
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        response.getWriter().write("Login Success");
        response.getWriter().write(objectMapper.writeValueAsString(authResult.getName()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(failed.getMessage()));
    }
}
