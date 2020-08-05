package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;
import org.greenbyme.angelhack.domain.mission.MissionCertificationMethod;
import org.greenbyme.angelhack.domain.mission.MissionRepository;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoRepository;
import org.greenbyme.angelhack.service.dto.mission.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final MissionInfoRepository missionInfoRepository;

    @Autowired
    private FileUploadDownloadService service;

    @Transactional
    public MissionSaveResponseDto save(MissionSaveRequestDto missionSaveRequestDto, MultipartFile file) {

        String fileName = service.storeFile(file);
        String filedUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/missions/images/")
                .path(fileName)
                .toUriString();
        MissionCertificationMethod missionCertificationMethod = MissionCertificationMethod.builder()
                .text(missionSaveRequestDto.getMissionCertificationText())
                .title(missionSaveRequestDto.getMissionCertificationTitle())
                .missionCertificateCount(missionSaveRequestDto.getMissionCertificateCount())
                .build();

        Mission mission = Mission.builder()
                .category(missionSaveRequestDto.getCategory())
                .subject(missionSaveRequestDto.getSubject())
                .description(missionSaveRequestDto.getDescription())
                .dayCategory(missionSaveRequestDto.getDayCategory())
                .expectCo2(missionSaveRequestDto.getExpectCo2())
                .pictureUrl(filedUrl)
                .missionCertificationMethod(missionCertificationMethod)
                .build();

        missionRepository.save(mission);
        return new MissionSaveResponseDto(mission);
    }

    public Page<MissionFindAllResponseDto> findAllMission(Pageable pageable) {
        return missionRepository.findAll(pageable).map(MissionFindAllResponseDto::new);
    }

    public Page<MissionFindAllByCategoryAndDayCategoryResponseDto> findAllByCategoryAndDayCategory(Category category, DayCategory dayCategory, Pageable pageable) {
        return missionRepository.findAllByCategoryAndDayCategory(category, dayCategory, pageable).map(MissionFindAllByCategoryAndDayCategoryResponseDto::new);
    }

    public Page<MissionFindAllByCategoryResponseDto> findAllByCategory(Category category, Pageable pageable) {
        return missionRepository.findAllByCategory(category, pageable).map(MissionFindAllByCategoryResponseDto::new);
    }

    @Transactional
    public MissionDeleteDto delete(Long id) {
        Mission mission = missionRepository.findById(id).orElseThrow(() -> new NoResultException("등록되지 않은 미션입니다."));
        List<MissionInfo> missionInfosByMission = missionInfoRepository.findByMission(mission);
        for (MissionInfo missionInfo : missionInfosByMission) {
            missionInfoRepository.delete(missionInfo);
        }
        missionRepository.delete(mission);
        return new MissionDeleteDto(mission);
    }

    public MissionDetailsDto findById(Long id) {
        Mission mission = missionRepository.findById(id).orElseThrow(() -> new NoResultException("등록되지 않은 미션입니다."));
        Long progressByMissionId = missionInfoRepository.findProgressByMissionId(id);
        return new MissionDetailsDto(mission, progressByMissionId);
    }

    public Page<MissionPopularResponseDto> findAllByPopular(Pageable pageable) {
        return missionRepository.findAll(pageable).map(MissionPopularResponseDto::new);
    }
}
