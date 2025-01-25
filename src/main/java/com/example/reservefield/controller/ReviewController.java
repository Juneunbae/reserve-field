package com.example.reservefield.controller;

import com.example.reservefield.domain.review.ReviewService;
import com.example.reservefield.dto.request.CreateReviewDto;
import com.example.reservefield.dto.request.UpdateReviewDto;
import com.example.reservefield.dto.response.ReservationsReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Description(
        "구장 이용 후기 작성하기"
    )
    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody CreateReviewDto create) {
        reviewService.createReview(create);
        return ResponseEntity.ok().build();
    }

    @Description(
        "내가 작성한 구장 이용 후기 전체조회"
    )
    @GetMapping("/my-reservations")
    public ResponseEntity<List<ReservationsReviewDto>> getReservationsReviews(
        @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(reviewService.getReservationsReviews(page));
    }

    @Description(
        "내가 작성한 구장 이용 후기 단일 조회"
    )
    @GetMapping("/my-reservations/{id}")
    public ResponseEntity<ReservationsReviewDto> getReservationsReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReservationsReview(id));
    }

    @Description(
        "작성한 이용 후기 수정하기"
    )
    @PutMapping("/my-reservations/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable Long id, @RequestBody UpdateReviewDto update) {
        reviewService.updateReview(id, update);
        return ResponseEntity.ok().build();
    }

    @Description(
        "작성한 이용 후기 삭제하기"
    )
    @DeleteMapping("/my-reservations/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}