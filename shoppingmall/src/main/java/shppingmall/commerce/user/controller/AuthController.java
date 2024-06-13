package shppingmall.commerce.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.repository.UserRepository;

// TODO User Entity 자체를 매우 단순하게 설계했음.

/**
 * Security 등을 통해 인증, 인가를 구현할 수 있지만, 일단은 보안을 전부 배제한
 * userName만 DB에서 조회 후 동일하다면 login하도록(Session을 생성하도록) 로직 구성
 * 추후 보강예정.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }


    @GetMapping("/")
    public String home() {
        return "index";
    }
    @PostMapping("/auth/login")
    public String login(@RequestParam Long userId, HttpSession httpSession) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원은 없습니다."));
        httpSession.setAttribute("user", user);
        log.info("로그인 및 Session 객체 생성 성공, session : {}", user);
        return "index";
    }
}
