package com.example.reservefield.domain.image;

import com.example.reservefield.domain.stadium.Stadium;
import com.example.reservefield.exception.CustomException;
import com.example.reservefield.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    @Value("${file.path}/")
    private String fileDirPath;

    @Transactional
    public void storeFile(Stadium stadium, List<MultipartFile> multipartFiles) {
        File fileRoot = new File(fileDirPath);

        if (!fileRoot.exists()) {
            fileRoot.mkdirs();
            log.info("files 폴더 생성 완료");
        }

        log.info(fileRoot.getAbsolutePath());

        if (multipartFiles == null) {
            log.info("빈 파일");
            return;
        }

        int count = 0;
        LocalDate today = LocalDate.now();
        for (MultipartFile file : multipartFiles) {
            log.info(file.getOriginalFilename());

            String fileName = today + "-" + file.getOriginalFilename() + "-" + count;
            File newFile = new File(fileDirPath, fileName);

            try {
                file.transferTo(newFile);
                log.info("파일명: {}, 저장 성공", fileName);

                Image newImage = imageMapper.toImageEntity(stadium, fileName, fileDirPath + fileName, false, LocalDateTime.now());
                stadium.addImage(newImage);
                log.info(newImage.toString());

            } catch (IOException e) {
                log.error(e.getMessage());

            } finally {
                count++;
            }
        }
    }

    @Transactional
    public void deleteByStadiumId(Long id, Long stadiumId) {
        Image image = imageRepository.findByIdAndStadiumId(id, stadiumId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구장 이미지 정보가 존재하지 않습니다."));

        imageRepository.delete(image);
        log.info("구장 - {}, 이미지 - {}, 삭제 완료", stadiumId, id);

        File file = new File(image.getPath());

        if (file.exists()) {
            file.delete();
            log.info("파일 - {}, 삭제 완료", image.getPath());
        }
    }
}