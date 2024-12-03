package shppingmall.commerce.global.filter.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import shppingmall.commerce.auth.dto.CustomUserDetails;
import shppingmall.commerce.global.ApiResponse;
import shppingmall.commerce.global.exception.ApiException;
import shppingmall.commerce.global.exception.ErrorCode;
import shppingmall.commerce.global.exception.domain.AuthErrorCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Optional<String> optionalAuthorization = Optional.ofNullable(request.getHeader("Authorization"));

        // 토큰이 없을때
        if (!optionalAuthorization.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!optionalAuthorization.map(auth -> auth.startsWith("Bearer ")).orElse(false)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // TODO : APIEXCEPTION을 던져야하지 않을까?
            // 그러나 Filter단에서 Exception을 던져주는게 맞을까?
            ApiResponse<Object> responseDto = ApiResponse.of(HttpStatus.UNAUTHORIZED, AuthErrorCode.INVALID_LOGIN_REQUEST);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String responseDtoStr = objectMapper.writeValueAsString(responseDto);
            byte[] utf8JsonString = responseDtoStr.getBytes(StandardCharsets.UTF_8);
            response.getOutputStream().write(utf8JsonString);
            return;
        }

        String token = optionalAuthorization.map(auth -> auth.split(" ")[1]).orElse(null);

        if (jwtUtil.isExpired(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ApiResponse<Object> responseDto = ApiResponse.of(HttpStatus.UNAUTHORIZED, AuthErrorCode.EXPIRED_TOKEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String responseDtoStr = objectMapper.writeValueAsString(responseDto);
            byte[] utf8JsonString = responseDtoStr.getBytes(StandardCharsets.UTF_8);
            response.getOutputStream().write(utf8JsonString);
            return;
        }


        CustomUserDetails customUserDetails = CustomUserDetails
                .builder().id(jwtUtil.getId(token)).username(jwtUtil.getEmail(token)).userRole(jwtUtil.getRole(token)).build();

        // 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());

        // (임시) 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);










    }
}
