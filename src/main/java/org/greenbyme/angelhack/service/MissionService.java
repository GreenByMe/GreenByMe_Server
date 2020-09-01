package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

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
