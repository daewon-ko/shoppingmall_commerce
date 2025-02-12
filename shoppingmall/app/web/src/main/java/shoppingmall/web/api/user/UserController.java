package shoppingmall.web.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoppingmall.domainrdb.user.entity.UserRole;
import shoppingmall.domainservice.domain.user.dto.request.CreateUserRequestDto;
import shoppingmall.domainservice.domain.user.dto.request.LoginUserRequestDto;
import shoppingmall.web.api.user.usecase.UserUsecase;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class UserController {
    private final UserUsecase userUsecase;

//    @GetMapping("/auth/login")
//    public String login(Model model) {
//        LoginUserRequestDto loginUserRequestDto = LoginUserRequestDto.builder()
//                .build();
//        model.addAttribute("loginUserRequestDto", loginUserRequestDto);
//
//        return "/login";
//    }


//    @GetMapping("/users/signup")
//    public String signUP(Model model) {
//        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
//                .build();
//        model.addAttribute("memberForm", createUserRequestDto);
//        return "signUp";
//    }

    @PostMapping("/users/signup/buyer")
    public ResponseEntity<Long> signUpBuyer(@Valid @RequestBody CreateUserRequestDto createUserRequest) {
        final UserRole userRole = UserRole.BUYER;
        Long registeredUserId = userUsecase.registerUser(createUserRequest,userRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserId);
    }

    @PostMapping("/users/signup/seller")
    public ResponseEntity<Long> singUpSeller(@Valid @RequestBody CreateUserRequestDto createUserRequest) {
        UserRole userRole = UserRole.SELLER;
        Long registeredUserId = userUsecase.registerUser(createUserRequest, userRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserId);

    }
}
