package com.example.reservefield.domain.review;

import com.example.reservefield.domain.CircularService;
import com.example.reservefield.domain.reserve.Reserve;
import com.example.reservefield.domain.user.User;
import com.example.reservefield.dto.request.CreateReviewDto;
import com.example.reservefield.dto.request.UpdateReviewDto;
import com.example.reservefield.dto.response.ReservationsReviewDto;
import com.example.reservefield.exception.CustomException;
import com.example.reservefield.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CircularService circularService;
    private final ReviewMapper reviewMapper;

    @Transactional
    public void createReview(CreateReviewDto create) {
        User user = circularService.getAuthenticationService().getLoginUser();
        Reserve reserve = circularService.getReserveService().getReserveById(create.reserveId());

        if (reviewRepository.existsByReserveIdAndReserveTimeIdAndReserveDate(create.reserveId(), create.reserveTimeId(), create.reserveDate())) {
            log.error("이미 작성된 리뷰가 존재");
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 작성된 이용 후기가 존재하는 예약 정보입니다.");
        }

        if (!Objects.equals(user.getId(), reserve.getUser().getId())) {
            log.error("로그인 사용자 - {}, 예약자 - {}, 동일하지 않는 예약자", user.getId(), reserve.getUser().getId());
            throw new CustomException(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다.");
        }

        if (!Objects.equals(create.stadiumId(), reserve.getStadium().getId())) {
            log.error("예약 후기 요청 구장 - {}, 예약된 구장 - {}, 동일하지 않는 구장 정보", create.stadiumId(), reserve.getStadium().getId());
            throw new CustomException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        }

        if (!Objects.equals(create.reserveTimeId(), reserve.getReserveTime().getId())) {
            log.error("예약 후기 요청 시간 - {}, 예약된 시간 - {}, 동일하지 않은 예약 시간", create.reserveTimeId(), reserve.getReserveTime().getId());
            throw new CustomException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        }

        if (!Objects.equals(create.reserveDate(), reserve.getReserveDate())) {
            log.error("예약 후기 요청 날짜 - {}, 예약된 날짜 - {}, 동일하지 않은 예약 날짜", create.reserveDate(), reserve.getReserveDate());
            throw new CustomException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        }

        Review newReview = Review.builder()
            .user(user)
            .stadium(reserve.getStadium())
            .reserve(reserve)
            .reserveDate(create.reserveDate())
            .reserveTime(reserve.getReserveTime())
            .satisfaction(create.satisfaction())
            .content(create.content())
            .createdAt(LocalDateTime.now())
            .createdBy(user.getId())
            .build();

        reviewRepository.save(newReview);
        log.error("예약 번호 - {}, 예약 후기 작성", reserve.getId());
    }

    @Transactional(readOnly = true)
    public List<ReservationsReviewDto> getReservationsReviews(int page) {
        User user = circularService.getAuthenticationService().getLoginUser();

        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);

        return reviewRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId(), pageable).stream().map(
            reviewMapper::toMyReserveReviewDto
        ).toList();
    }

    @Transactional(readOnly = true)
    public ReservationsReviewDto getReservationsReview(Long id) {
        User user = circularService.getAuthenticationService().getLoginUser();

        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 이용 후기 입니다."));

        if (!Objects.equals(user.getId(), review.getUser().getId())) {
            log.error("로그인 사용자 - {}, 이용 후기 작성자 - {}, 동일하지 않는 사용자 정보", user.getId(), review.getUser().getId());
            throw new CustomException(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다.");
        }

        return reviewMapper.toMyReserveReviewDto(review);
    }

    @Transactional
    public void updateReview(Long id, UpdateReviewDto update) {
        User user = circularService.getAuthenticationService().getLoginUser();

        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 이용 후기 정보입니다."));

        if (!Objects.equals(user.getId(), review.getUser().getId())) {
            log.error("로그인 사용자 - {}, 이용 후기 작성자 - {}, 동일하지 않는 사용자 정보", user.getId(), review.getUser().getId());
            throw new CustomException(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다.");
        }

        review.update(update, user);
        log.error("이용 후기 - {}, 수정 완료", review.getId());
    }

    @Transactional
    public void deleteReview(Long id) {
        User user = circularService.getAuthenticationService().getLoginUser();

        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 이용 후기 정보입니다."));

        if (!Objects.equals(user.getId(), review.getUser().getId())) {
            log.error("로그인 사용자 - {}, 이용 후기 작성자 - {}, 동일하지 않는 사용자 정보", user.getId(), review.getUser().getId());
            throw new CustomException(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다.");
        }

        reviewRepository.delete(review);
        log.error("이용 후기 삭제 완료");
    }

    @Transactional(readOnly = true)
    public List<ReservationsReviewDto> getReviews(int page) {
        User admin = circularService.getAuthenticationService().getAdmin();
        Pageable pageable = PageRequest.of(page - 1, 10);

        return reviewRepository.findAllByAdminId(admin.getId(), pageable).stream().map(
            reviewMapper::toMyReserveReviewDto
        ).toList();
    }

    @Transactional(readOnly = true)
    public ReservationsReviewDto getReview(Long id) {
        User admin = circularService.getAuthenticationService().getAdmin();

        Review review = reviewRepository.findByReviewId(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "이용 후기 정보가 존재하지 않습니다."));

        if (!Objects.equals(admin.getId(), review.getUser().getId())) {
            log.error("해당 구장에 권한 정보가 없는 ADMIN");
            throw new CustomException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
        }

        return reviewMapper.toMyReserveReviewDto(review);
    }
}