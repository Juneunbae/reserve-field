package com.example.reservefield.controller;

import com.example.reservefield.domain.user.UserService;
import com.example.reservefield.dto.request.CheckPasswordRequestDto;
import com.example.reservefield.dto.request.LoginRequestDto;
import com.example.reservefield.dto.request.SignupRequestDto;
import com.example.reservefield.dto.request.UpdateMyInfoRequestDto;
import com.example.reservefield.dto.response.MyInfoDto;
import com.example.reservefield.dto.response.TokenInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Description(
        "회원 가입"
    )
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Validated @RequestBody SignupRequestDto signupRequestDto, Errors errors) {
        userService.signup(signupRequestDto, errors);
        return ResponseEntity.ok().build();
    }

    @Description(
        "로그인"
    )
    @PostMapping("/login")
    public ResponseEntity<TokenInfoDto> login(@Validated @RequestBody LoginRequestDto loginRequestDto, Errors errors) {
        return ResponseEntity.ok(userService.login(loginRequestDto, errors));
    }

    @Description(
        "이메일 중복 검사"
    )
    @GetMapping("/check/email")
    public ResponseEntity<Void> checkEmail(@RequestParam String email) {
        userService.checkEmail(email);
        return ResponseEntity.ok().build();
    }

    @Description(
        "비밀번호 인증"
    )
    @PostMapping("/check/password")
    public ResponseEntity<Void> checkPassword(@Validated @RequestBody CheckPasswordRequestDto checkPasswordRequestDto, Errors errors) {
        userService.checkPassword(checkPasswordRequestDto, errors);
        return ResponseEntity.ok().build();
    }

    @Description(
        "내 정보 가져오기"
    )
    @GetMapping("/{id}/my-info")
    public ResponseEntity<MyInfoDto> getMyInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getMyInfo(id));
    }

    @Description(
        "내 정보 수정하기 - 비밀번호"
    )
    @PutMapping("/{id}/my-info")
    public ResponseEntity<Void> updateMyInfo(@PathVariable Long id, @Validated @RequestBody UpdateMyInfoRequestDto updateMyInfoRequestDto, Errors errors) {
        userService.updateMyInfo(id, updateMyInfoRequestDto, errors);
        return ResponseEntity.ok().build();
    }

    @Description(
        "회원 탈퇴"
    )
    @DeleteMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id) {
        userService.withdraw(id);
        return ResponseEntity.ok().build();
    }
}