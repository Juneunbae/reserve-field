package com.example.reservefield.domain.stadium;

import com.example.reservefield.domain.CircularService;
import com.example.reservefield.domain.reserve.Status;
import com.example.reservefield.domain.user.User;
import com.example.reservefield.dto.request.CreateStadiumRequestDto;
import com.example.reservefield.dto.request.UpdateStadiumRequestDto;
import com.example.reservefield.dto.response.*;
import com.example.reservefield.exception.CustomException;
import com.example.reservefield.exception.ValidationErrors;
import com.example.reservefield.mapper.ImageMapper;
import com.example.reservefield.mapper.StadiumMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;
    private final StadiumMapper stadiumMapper;
    private final ValidationErrors validationErrors;
    private final ImageMapper imageMapper;
    private final CircularService circularService;
    private final int size = 10;

    public StadiumInfosDto getStadiumInfos(
        String name,
        Address address,
        StadiumSize stadiumSize,
        StadiumType stadiumType,
        int page,
        DoorType doorType
    ) {
        // TODO: 임직원 로그인

        Pageable pageable = PageRequest.of(page - 1, size);

        log.info("검색 조건 - {}, {}, {}, {}", name, address, stadiumSize, stadiumType);

        List<Stadium> stadiums = stadiumRepository.findAllStadiumList(pageable, name, address, stadiumSize, stadiumType, doorType);
        Integer totalElements = stadiumRepository.countAllStadiumList(name, address, stadiumSize, stadiumType, doorType);
        Integer totalPages = stadiums.size() / size;

        log.info("모든 구장 정보 가져오기 성공");

        List<StadiumInfoDto> stadiumInfoDto = stadiums.stream()
            .map(stadiumMapper::toStadiumInfoDto)
            .toList();

        return stadiumMapper.toStadiumInfosDto(stadiumInfoDto, totalPages, totalElements);
    }

    public StadiumDetailDto getStadiumDetailInfo(Long id) {
        // TODO: 임직원 로그인

        Stadium stadium = stadiumRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 구장 정보입니다."));

        return stadiumMapper.toStadiumDetailDto(stadium);
    }

    @Transactional
    public void createStadium(List<CreateStadiumRequestDto> CreateStadiumRequestDtoList, Errors errors) {
        User admin = circularService.getAuthenticationService().getAdmin();
        validationErrors.checkDtoErrors(errors, "구장 신청에 일치하지 않는 양식입니다.");

        for (CreateStadiumRequestDto create : CreateStadiumRequestDtoList) {
            log.info("{}", create.openingTime());

            if (create.openingTime().isAfter(create.closingTime())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "오픈 시간은 마감 시간보다 늦을 수 없습니다");
            }

            Stadium stadium = Stadium.builder()
                .admin(admin)
                .name(create.name())
                .sizeX(create.sizeX())
                .sizeY(create.sizeY())
                .size(create.size())
                .isUsed(false)
                .price(create.price())
                .doorType(create.doorType())
                .stadiumType(create.stadiumType())
                .coverImage(create.coverImage())
                .address(create.address())
                .detailAddress(create.detailAddress())
                .openingTime(create.openingTime())
                .closingTime(create.closingTime())
                .isParking(create.isParking())
                .isParkingFree(create.isParkingFree())
                .parkingInfo(create.parkingInfo())
                .isShower(create.isShower())
                .isShowerFree(create.isShowerFree())
                .showerInfo(create.showerInfo())
                .isWear(create.isWear())
                .isWearFree(create.isWearFree())
                .wearInfo(create.wearInfo())
                .isShoes(create.isShoes())
                .isShoesFree(create.isShoesFree())
                .shoesInfo(create.shoesInfo())
                .isBall(create.isBall())
                .ballInfo(create.ballInfo())
                .isToilet(create.isToilet())
                .intro(create.intro())
                .promise(create.promise())
                .createdAt(LocalDateTime.now())
                .createdBy(admin.getId())
                .build();

            stadiumRepository.save(stadium);
            log.info("구장: {}, 신청 완료", create.name());
        }
    }

    @Transactional
    public void updateStadium(Long stadiumId, UpdateStadiumRequestDto update, Errors errors) {
        validationErrors.checkDtoErrors(errors, "구장 정보 수정에 일치하지 않는 양식입니다.");

        Stadium stadium = stadiumRepository.findById(stadiumId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 정보가 존재하지 않습니다."));

        stadium.update(update);
        stadiumRepository.save(stadium);

        log.info("구장 정보 수정 완료");
    }

    @Transactional
    public void deleteStadium(Long stadiumId) {
        User admin = circularService.getAuthenticationService().getAdmin();

        LocalDate today = LocalDate.now();

        Stadium stadium = stadiumRepository.findByIdAndAdmin(stadiumId, admin)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 정보가 존재하지 않거나 권한이 없습니다."));

        if (circularService.getReserveService().existsReserveByStadiumIdAndReserveDate(stadium.getId(), today)) {
            log.error("예약이 존재하므로 삭제할 수 없습니다.");
            throw new CustomException(HttpStatus.BAD_REQUEST, "예약이 존재하므로 삭제할 수 없습니다.");
        }

        stadiumRepository.delete(stadium);
        log.info("구장: {}, 삭제되었습니다.", stadiumId);
    }

    @Transactional
    public List<ImageInfoDto> getStadiumImages(Long id) {
        Stadium stadium = stadiumRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 정보가 존재하지 않습니다."));

        return stadium.getImages().stream()
            .map(imageMapper::toImageInfoDto)
            .toList();
    }

    @Transactional
    public void addStadiumImage(Long id, List<MultipartFile> files) {
        User admin = circularService.getAuthenticationService().getAdmin();

        Stadium stadium = stadiumRepository.findByIdAndAdminId(id, admin.getId())
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 정보가 존재하지 않습니다."));

        circularService.getImageService().storeFile(stadium, files);
    }

    @Transactional
    public void deleteStadiumImage(Long id, Long imageId) {
        User admin = circularService.getAuthenticationService().getAdmin();

        Stadium stadium = stadiumRepository.findByIdAndAdminId(id, admin.getId())
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 정보가 존재하지 않습니다."));

        circularService.getImageService().deleteByStadiumId(imageId, stadium.getId());
    }

    @Transactional(readOnly = true)
    public List<StadiumReserveTimeDto> getStadiumReserveTime(Long id, LocalDate reserveDate) {
        LocalDate date = reserveDate == null ? LocalDate.now() : reserveDate;

        if (reserveDate.isBefore(LocalDate.now())) {
            log.error("오늘보다 이전 날짜 검색");
            throw new CustomException(HttpStatus.BAD_REQUEST, "지난 날짜는 검색할 수 없습니다.");
        }

        Stadium stadium = stadiumRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 정보가 존재하지 않습니다."));

        if (!stadium.getIsUsed()) {
            log.error("운영 중지 구장 정보: {}", id);
            throw new CustomException(HttpStatus.SERVICE_UNAVAILABLE, "운영이 중지된 구장 정보입니다.");
        }

        return circularService.getReserveService().getAvailableReserveTimes(stadium.getId(), date).stream()
            .map(
                availableReserveTime -> stadiumMapper.toStadiumReserveDto(
                    id, date, availableReserveTime, Status.AVAILABLE
                )
            ).toList();
    }

    @Transactional(readOnly = true)
    public StadiumReserveDto getStadiumReserve(
        int page,
        LocalDate reserveDate,
        String name,
        Address address,
        StadiumSize stadiumSize,
        StadiumType stadiumType,
        DoorType doorType
    ) {
        log.info("검색 조건 - 예약일: {}, 이름: {}, 지역: {}, 구장 크기: {}, 구장 종류: {}, 바닥 종류: {}", reserveDate, name, address, stadiumSize, stadiumType, doorType);
        LocalDate date = reserveDate == null ? LocalDate.now() : reserveDate;

        if (reserveDate.isBefore(LocalDate.now())) {
            log.error("오늘보다 이전 날짜 검색");
            throw new CustomException(HttpStatus.BAD_REQUEST, "지난 날짜는 검색할 수 없습니다.");
        }

        Pageable pageable = PageRequest.of(page - 1, size);

        List<Stadium> stadiums = stadiumRepository.findAllStadiumInfoList(pageable, name, address, stadiumSize, stadiumType, doorType);
        List<StadiumReserveDetailDto> stadiumReserveDetailDto = stadiums.stream().map(
            stadium -> stadiumMapper.toStadiumReserveDetailDto(
                stadium,
                this.getStadiumImages(stadium.getId()),
                this.getStadiumReserveTime(stadium.getId(), date)
            )
        ).toList();

        return new StadiumReserveDto(stadiumReserveDetailDto, stadiums.size(), stadiums.size() / size);
    }

    @Transactional
    public Stadium getStadiumById(Long id) {
        return stadiumRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 정보가 존재하지 않습니다."));
    }
}