package com.example.reservefield.domain.user;

import com.example.reservefield.common.security.JwtUtils;
import com.example.reservefield.dto.request.CheckPasswordRequestDto;
import com.example.reservefield.dto.request.LoginRequestDto;
import com.example.reservefield.dto.request.SignupRequestDto;
import com.example.reservefield.dto.request.UpdateMyInfoRequestDto;
import com.example.reservefield.dto.response.MyInfoDto;
import com.example.reservefield.dto.response.TokenInfoDto;
import com.example.reservefield.exception.CustomException;
import com.example.reservefield.exception.ValidationErrors;
import com.example.reservefield.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDetailService userDetailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationErrors validationErrors;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto, Errors errors) {
        validationErrors.checkDtoErrors(errors, "회원가입 양식에 일치하지 않는 양식이 있습니다.");

        if (userRepository.existsByEmail(signupRequestDto.email())) {
            throw new CustomException(HttpStatus.ALREADY_REPORTED, "이미 사용 중인 이메일 입니다.");
        }

        User newUser = User.builder()
            .email(signupRequestDto.email())
            .password(passwordEncoder.encode(signupRequestDto.password()))
            .name(signupRequestDto.name())
            .phone(signupRequestDto.phone())
            .role(Role.PLAYER)
            .status(Status.WAITING)
            .login_fail_count(0)
            .created_at(LocalDateTime.now())
            .build();

        userRepository.save(newUser);
        log.info("사용자 생성 완료: {}", signupRequestDto.email());

        // TODO: 이메일 인증 로직 추가하기
    }

    @Transactional
    public TokenInfoDto login(LoginRequestDto loginRequestDto, Errors errors) {
        validationErrors.checkDtoErrors(errors, "잘못된 이메일 양식입니다.");

        User user = userRepository.findByEmail(loginRequestDto.email())
            .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            log.error("사용자 로그인 오류로 인해 실패횟수 증가");
            userDetailService.updateFailCount(user);

            if (user.getLogin_fail_count() > 5) {
                log.error("실패 횟수 5회 초과 사용자");
                userDetailService.updateStatusStopped(user);
            }

            throw new CustomException(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        if (user.getStatus() != Status.COMPLETE) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "인증이 완료되지 않은 사용자이거나 정지된 사용자입니다.");
        }

        user.successLogin();

        log.info("사용자:{}, 로그인 성공", user.getId());
        return jwtUtils.generateToken(user);
    }

    @Transactional
    public void checkEmail(String email) {
        String emailRegexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        if (!email.matches(emailRegexp)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "잘못된 이메일 형식입니다.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new CustomException(HttpStatus.ALREADY_REPORTED, "이미 사용 중인 이메일입니다.");
        }

        log.info("이메일 중복 여부 검사 통과: {}", email);
    }

    @Transactional
    public void checkPassword(CheckPasswordRequestDto checkPasswordRequestDto, Errors errors) {
        validationErrors.checkDtoErrors(errors, "잘못된 이메일 양식입니다.");

        User user = userRepository.findPasswordByEmail(checkPasswordRequestDto.email())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(checkPasswordRequestDto.password(), user.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        log.info("비밀번호 인증 성공: {}", checkPasswordRequestDto.email());
    }

    @Transactional(readOnly = true)
    public MyInfoDto getMyInfo(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        log.info("사용자: {}, 내 정보 불러오기 성공", id);
        return userMapper.toMyInfoDto(user);
    }

    @Transactional
    public void updateMyInfo(Long id, UpdateMyInfoRequestDto updateMyInfoRequestDto, Errors errors) {
        validationErrors.checkDtoErrors(errors, "잘못된 비밀번호 형식입니다.");

        User user = userRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        if (passwordEncoder.matches(updateMyInfoRequestDto.newPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "같은 비밀번호로 바꿀 수 없습니다.");
        }

        user.changePassword(passwordEncoder.encode(updateMyInfoRequestDto.newPassword()));
        log.info("사용자: {}, 비밀번호 변경 성공", user.getId());
    }

    @Transactional
    public void withdraw(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        // TODO: 만약 예약된 정보가 있을 경우, 삭제 반려

        userRepository.delete(user);
        log.info("사용자: {}, 탈퇴 성공", user.getId());
    }
}