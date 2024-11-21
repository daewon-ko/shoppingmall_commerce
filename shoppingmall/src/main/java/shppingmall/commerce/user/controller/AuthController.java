package shppingmall.commerce.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shppingmall.commerce.cart.dto.request.CreateCartRequestDto;
import shppingmall.commerce.user.dto.CreateUserRequestDto;
import shppingmall.commerce.user.dto.LoginUserRequestDto;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.repository.UserRepository;
import shppingmall.commerce.user.service.UserService;

// TODO User Entity 자체를 매우 단순하게 설계했음.

/**
 * Security 등을 통해 인증, 인가를 구현할 수 있지만, 일단은 보안을 전부 배제한
 * userName만 DB에서 조회 후 동일하다면 login하도록(Session을 생성하도록) 로직 구성
 * 추후 보강예정.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login(Model model) {
        LoginUserRequestDto loginUserRequestDto = LoginUserRequestDto.builder()
                .build();
        model.addAttribute("loginUserRequestDto", loginUserRequestDto);

        return "/login";
    }


    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/auth/login")
    public String login(@Valid LoginUserRequestDto loginUserRequestDto, HttpSession httpSession) {
        userService.login(loginUserRequestDto, httpSession);
        return "index";
    }

    @GetMapping("/auth/register")
    public String signUP(Model model) {
        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .build();
        model.addAttribute("memberForm", createUserRequestDto);
        return "signUp";
    }

    @PostMapping("/auth/register")
    public String singUp(@Valid @RequestBody CreateUserRequestDto createUserRequest) {

        userService.registerBuyer(createUserRequest);
        return "index";
    }
}
