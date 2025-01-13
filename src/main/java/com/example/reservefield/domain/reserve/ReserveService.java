package com.example.reservefield.domain.reserve;

import com.example.reservefield.domain.CircularService;
import com.example.reservefield.domain.stadium.Stadium;
import com.example.reservefield.domain.user.User;
import com.example.reservefield.dto.request.CreateReservationDto;
import com.example.reservefield.dto.response.ReserveHistoryDto;
import com.example.reservefield.dto.response.ReserveInfoDto;
import com.example.reservefield.exception.CustomException;
import com.example.reservefield.exception.ValidationErrors;
import com.example.reservefield.mapper.ReserveMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ValidationErrors validationErrors;
    private final ReserveTimeService reserveTimeService;
    private final ReserveRepository reserveRepository;
    private final ReserveMapper reserveMapper;
    private final CircularService circularService;
    private final ReserveHistoryService reserveHistoryService;

    @Transactional
    public void createReserve(CreateReservationDto create, Errors errors) {
        validationErrors.checkDtoErrors(errors, "구장 예약에 필요한 양식에 맞지 않는 데이터가 있습니다.");

        if (create.reserveDate().isAfter(LocalDate.now().plusDays(3))) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "현재는 예약이 불가능한 날짜입니다.");
        }

        // reserve 이미 있는지 검사하기
        if (reserveRepository.existsByStadiumIdAndReserveDateAndReserveTimeIdAndStatus(create.stadiumId(), create.reserveDate(), create.reserveTimeId(), Status.CONFIRM)) {
            log.error("이미 존재하는 예약 건");
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 존재하는 예약 정보입니다.");
        }

        User user = circularService.getAuthenticationService().getLoginUser();
        Stadium stadium = circularService.getStadiumService().getStadiumById(create.stadiumId());
        ReserveTime reserveTime = reserveTimeService.getReserveTimeById(create.reserveTimeId());

        Reserve newReserve = Reserve.builder()
            .stadium(stadium)
            .user(user)
            .reserveDate(create.reserveDate())
            .reserveTime(reserveTime)
            .status(Status.CONFIRM)
            .createdAt(LocalDateTime.now())
            .createdBy(user.getId())
            .build();

        reserveRepository.save(newReserve);

        reserveHistoryService.createHistory(user, stadium, reserveTime, create.reserveDate(), Status.CONFIRM);
        log.info("예약 완료,  예약자 - {}, 구장 - {}, 예약 날짜 - {}, 예약 시간대 - {}", user.getId(), stadium.getId(), create.reserveDate(), reserveTime.getDescription());
    }

    @Transactional
    public void cancelReserve(Long id) {
        User user = circularService.getAuthenticationService().getLoginUser();
        Reserve reserve = reserveRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 예약 정보입니다."));

        if (!Objects.equals(reserve.getUser().getId(), user.getId())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다.");
        }

        if (reserve.getReserveDate().isBefore(LocalDate.now())) {
            log.error("오늘보다 이전 데이터 취소 요청, 예약자 - {}, 예약 날짜 - {}", user.getId(), reserve.getReserveDate());
            throw new CustomException(HttpStatus.BAD_REQUEST, "지난 예약은 취소할 수 없습니다.");
        }

        reserveRepository.delete(reserve);
        reserveHistoryService.createHistory(user, reserve.getStadium(), reserve.getReserveTime(), reserve.getReserveDate(), Status.CANCELED);
    }

    @Transactional
    public List<ReserveInfoDto> getReserves() {
        User admin = circularService.getAuthenticationService().getAdmin();

        return reserveRepository.findAllByAdminId(admin.getId()).stream().map(
            reserveMapper::toReserveInfoDto
        ).toList();
    }

    @Transactional
    public ReserveInfoDto getReserve(Long id) {
        User admin = circularService.getAuthenticationService().getAdmin();

        Reserve reserve = reserveRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 예약 정보입니다."));
        Stadium stadium = circularService.getStadiumService().getStadiumById(reserve.getStadium().getId());

        if (!Objects.equals(admin.getId(), stadium.getAdmin().getId())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다.");
        }

        return reserveMapper.toReserveInfoDto(reserve);
    }

    @Transactional
    public List<ReserveTime> getAvailableReserveTimes(Long id, LocalDate reserveDate) {
        return reserveRepository.findAllByAvailableReserve(id, reserveDate);
    }

    @Transactional
    public Boolean existsReserveByStadiumIdAndReserveDate(Long stadiumId, LocalDate reserveDate) {
        return reserveRepository.existsByTodayReserve(reserveDate, stadiumId);
    }

    @Transactional
    public List<ReserveHistoryDto> getReservationHistories() {
        User user = circularService.getAuthenticationService().getLoginUser();

        return reserveHistoryService.getReserveHistoriesByUserId(user.getId()).stream().map(
            reserveMapper::toReserveHistoryDto
        ).toList();
    }

    @Transactional
    public ReserveHistoryDto getReservationHistory(Long id) {
        User user = circularService.getAuthenticationService().getLoginUser();

        ReserveHistory reserveHistory = reserveHistoryService.getReserveHistoryById(id);

        if (!reserveHistory.getUserId().equals(user.getId())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다.");
        }

        return reserveMapper.toReserveHistoryDto(reserveHistory);
    }

    @Transactional
    public Reserve getReserveById(Long id) {
        return reserveRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 예약 정보입니다."));
    }
}