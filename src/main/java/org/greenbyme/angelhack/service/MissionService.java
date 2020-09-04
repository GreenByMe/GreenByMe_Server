package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;
import org.greenbyme.angelhack.domain.mission.MissionCertificationMethod;
import org.greenbyme.angelhack.domain.mission.MissionRepository;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.MissionException;
import org.greenbyme.angelhack.service.dto.mission.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final PersonalMissionRepository personalMissionRepository;

    @Autowired
    private FileUploadDownloadService service;

    @Transactional
    public MissionSaveResponseDto save(MissionSaveRequestDto missionSaveRequestDto, MultipartFile file) {

        checkCategory(missionSaveRequestDto.getCategory());
        checkDayCategory(missionSaveRequestDto.getDayCategory());
        checkValidDayCategoryWithMissionCertificateCount(missionSaveRequestDto.getDayCategory(), missionSaveRequestDto.getMissionCertificateCount());

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
                .title(missionSaveRequestDto.getTitle())
                .missionCertificationMethod(missionCertificationMethod)
                .build();

        missionRepository.save(mission);
        return new MissionSaveResponseDto(mission);
    }

    private void checkValidDayCategoryWithMissionCertificateCount(DayCategory dayCategory, MissionCertificateCount missionCertificateCount) {
        String[] split = missionCertificateCount.toString().split("_");
        List<String> collect = Arrays.stream(split).collect(Collectors.toList());

        if (dayCategory.equals(DayCategory.DAY) && !collect.contains("DAY")) {
            throw new MissionException(ErrorCode.NOT_MATCH_VALUE);
        }
        if (dayCategory.equals(DayCategory.WEEK) && (collect.contains("DAY") || collect.contains("MONTH"))) {
            throw new MissionException(ErrorCode.NOT_MATCH_VALUE);
        }
        if (dayCategory.equals(DayCategory.MONTH) && !collect.contains("MONTH")) {
            throw new MissionException(ErrorCode.NOT_MATCH_VALUE);
        }
    }

    private void checkDayCategory(DayCategory dayCategory) {
        if (ObjectUtils.isEmpty(dayCategory) || dayCategory.equals(DayCategory.ALL)) {
            throw new MissionException(ErrorCode.CAN_NOT_ASSIGN_ALL_IN_DAYCATEGORY);
        }
    }

    private void checkCategory(Category category) {
        if (ObjectUtils.isEmpty(category) || category.equals(Category.ALL)) {
            throw new MissionException(ErrorCode.CAN_NOT_ASSIGN_ALL_IN_CATEGORY);
        }
    }

    public Page<MissionFindAllByCategoryAndDayCategoryResponseDto> findAllByCategoryAndDayCategory(Category category, DayCategory dayCategory, Pageable pageable) {
        return missionRepository.findAllByCategoryAndDayCategory(category, dayCategory, pageable);
    }

    @Transactional
    public MissionDeleteDto delete(Long id) {
        Mission mission = findMissionById(id);
        List<PersonalMission> personalMissionsByPersonalMission = personalMissionRepository.findByMission(mission);
        for (PersonalMission personalMission : personalMissionsByPersonalMission) {
            personalMissionRepository.delete(personalMission);
        }
        missionRepository.delete(mission);
        return new MissionDeleteDto(mission);
    }

    public MissionDetailsDto findById(Long id) {
        Mission mission = findMissionById(id);
        Long progressByMissionId = personalMissionRepository.findProgressByMissionId(id);
        return new MissionDetailsDto(mission, progressByMissionId);
    }

    public Page<MissionPopularResponseDto> findAllByPopular(Pageable pageable) {
        return missionRepository.findAll(pageable).map(MissionPopularResponseDto::new);
    }

    private Mission findMissionById(Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(ErrorCode.INVALID_MISSION));
    }
}
