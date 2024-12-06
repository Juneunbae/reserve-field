package com.example.reservefield.controller;

import com.example.reservefield.common.Response;
import com.example.reservefield.domain.user.UserService;
import com.example.reservefield.dto.request.CheckPasswordRequestDto;
import com.example.reservefield.dto.request.LoginRequestDto;
import com.example.reservefield.dto.request.SignupRequestDto;
import com.example.reservefield.dto.request.UpdateMyInfoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Description(
        "회원 가입"
    )
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Validated @RequestBody SignupRequestDto signupRequestDto, Errors errors) {
        userService.signup(signupRequestDto, errors);
        return Response.ok("회원가입 완료");
    }

    @Description(
        "로그인"
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Validated @RequestBody LoginRequestDto loginRequestDto, Errors errors) {
        return Response.ok(userService.login(loginRequestDto, errors));
    }

    @Description(
        "이메일 중복 검사"
    )
    @GetMapping("/check/email")
    public ResponseEntity<Map<String, String>> checkEmail(@RequestParam String email) {
        userService.checkEmail(email);
        return Response.ok("이메일 중복 검사 성공");
    }

    @Description(
        "비밀번호 인증"
    )
    @PostMapping("/check/password")
    public ResponseEntity<Map<String, String>> checkPassword(@Validated @RequestBody CheckPasswordRequestDto checkPasswordRequestDto, Errors errors) {
        userService.checkPassword(checkPasswordRequestDto, errors);
        return Response.ok("비밀번호 인증 성공");
    }

    @Description(
        "내 정보 가져오기"
    )
    @GetMapping("/{id}/my-info")
    public ResponseEntity<Map<String, Object>> getMyInfo(@PathVariable Long id) {
        return Response.ok(userService.getMyInfo(id));
    }

    @Description(
        "내 정보 수정하기 - 비밀번호"
    )
    @PutMapping("/{id}/my-info")
    public ResponseEntity<Map<String, String>> updateMyInfo(@PathVariable Long id, @Validated @RequestBody UpdateMyInfoRequestDto updateMyInfoRequestDto, Errors errors) {
        userService.updateMyInfo(id, updateMyInfoRequestDto, errors);
        return Response.ok("내 정보 수정 완료");
    }
}