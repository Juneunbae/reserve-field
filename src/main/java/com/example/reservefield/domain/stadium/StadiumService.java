package com.example.reservefield.domain.stadium;

import com.example.reservefield.domain.user.User;
import com.example.reservefield.domain.user.UserRepository;
import com.example.reservefield.dto.request.CreateStadiumRequestDto;
import com.example.reservefield.dto.request.UpdateStadiumRequestDto;
import com.example.reservefield.dto.response.StadiumDetailDto;
import com.example.reservefield.dto.response.StadiumInfoDto;
import com.example.reservefield.dto.response.StadiumInfosDto;
import com.example.reservefield.exception.CustomException;
import com.example.reservefield.exception.ValidationErrors;
import com.example.reservefield.mapper.StadiumMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;
    private final StadiumMapper stadiumMapper;
    private final UserRepository userRepository;
    private final ValidationErrors validationErrors;

    public StadiumInfosDto getStadiumInfos(
        String name,
        Address address,
        StadiumSize stadiumSize,
        StadiumType stadiumType,
        int page
    ) {
        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);

        log.info("{}, {}, {}, {}", name, address, stadiumSize, stadiumType);

        List<Stadium> stadiums = stadiumRepository.findAllStadiumList(pageable, name, address, stadiumSize, stadiumType);
        Integer totalElements = stadiumRepository.countAllStadiumList(name, address, stadiumSize, stadiumType);
        Integer totalPages = stadiums.size() / size;

        log.info("모든 구장 정보 가져오기 성공");

        List<StadiumInfoDto> stadiumInfoDto = stadiums.stream()
            .map(stadiumMapper::toStadiumInfoDto)
            .toList();

        return stadiumMapper.toStadiumInfosDto(stadiumInfoDto, totalPages, totalElements);
    }

    public StadiumDetailDto getStadiumDetailInfo(Long id) {
        Stadium stadium = stadiumRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 구장 정보입니다."));

        return stadiumMapper.toStadiumDetailDto(stadium);
    }

    public void createStadium(List<CreateStadiumRequestDto> CreateStadiumRequestDtoList, Errors errors) {
        // TODO: 로그인 유저가 ADMIN 인지 검사
        validationErrors.checkDtoErrors(errors, "구장 신청에 일치하지 않는 양식입니다.");

        for (CreateStadiumRequestDto create : CreateStadiumRequestDtoList) {
            if (create.openingTime().isAfter(create.closingTime())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "오픈 시간은 마감 시간보다 늦을 수 없습니다");
            }

            User admin = userRepository.findById(create.adminId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

            // TODO: ADMIN id 값,  현재 로그인한 유저의 id 값으로 변경
            Stadium stadium = Stadium.builder()
                .admin(admin)
                .name(create.name())
                .xSize(create.xSize())
                .ySize(create.ySize())
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
                .createdBy(1L) // TODO: 로그인한 관리자 ADMIN id 값으로 변경
                .build();

            stadiumRepository.save(stadium);
            log.info("구장: {}, 신청 완료", create.name());
        }
    }

    @Transactional
    public void updateStadium(Long stadiumId, UpdateStadiumRequestDto update, Errors errors) {
        // TODO: 유저가 ADMIN 인지 검사 필요
        validationErrors.checkDtoErrors(errors, "구장 정보 수정에 일치하지 않는 양식입니다.");

        Stadium stadium = stadiumRepository.findById(stadiumId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 정보가 존재하지 않습니다."));

        stadium.update(update);
        stadiumRepository.save(stadium);

        log.info("구장 정보 수정 완료");
    }

    @Transactional
    public void deleteStadium(Long stadiumId) {
        // TODO: 유저가 ADMIN 인지 검사
        // TODO: 예약이 있으면 삭제불가, 예약이 없으면 삭제 가능

        Stadium stadium = stadiumRepository.findById(stadiumId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 정보가 존재하지 않습니다."));

        stadiumRepository.delete(stadium);
        log.info("구장: {}, 삭제되었습니다.", stadiumId);
    }
}