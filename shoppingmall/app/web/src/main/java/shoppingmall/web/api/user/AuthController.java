package shoppingmall.web.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shoppingmall.domainrdb.user.dto.CreateUserRequestDto;
import shoppingmall.domainrdb.user.dto.LoginUserRequestDto;
import shoppingmall.domainrdb.user.repository.UserRepository;
import shoppingmall.domainrdb.user.service.UserRdbService;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class AuthController {
    private final UserRdbService userRdbService;
    private final UserRepository userRepository;

    @GetMapping("/auth/login")
    public String login(Model model) {
        LoginUserRequestDto loginUserRequestDto = LoginUserRequestDto.builder()
                .build();
        model.addAttribute("loginUserRequestDto", loginUserRequestDto);

        return "/login";
    }





    @GetMapping("/auth/register")
    public String signUP(Model model) {
        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .build();
        model.addAttribute("memberForm", createUserRequestDto);
        return "signUp";
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Long> singUp(@Valid @RequestBody CreateUserRequestDto createUserRequest) {

        Long registeredUserId = userRdbService.registerBuyer(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserId);
    }

    @PostMapping("/auth/seller/register")
    public String singUpSeller(@Valid @RequestBody CreateUserRequestDto createUserRequest) {

        userRdbService.registerSeller(createUserRequest);
        return "index";
    }
}
