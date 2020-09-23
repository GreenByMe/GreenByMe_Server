package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionRepository;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionRepository;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionStatus;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.MissionException;
import org.greenbyme.angelhack.exception.PersonalMissionException;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.dto.personalmission.InProgressResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDeleteResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDetailResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionSaveResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonalMissionService {

    private final PersonalMissionRepository personalMissionRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public PersonalMissionSaveResponseDto save(Long userId, Long missionId) {
        User user = findByUserId(userId);
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(ErrorCode.INVALID_MISSION));

        List<PersonalMission> personalMissionByUserIdAndWhereInProgresses = personalMissionRepository.findPersonalMissionByUserIdAndWhereInProgress(userId);

        for (PersonalMission personalMissionInProgress : personalMissionByUserIdAndWhereInProgresses) {
            if (personalMissionInProgress.getMission().getId() == mission.getId()) {
                throw new PersonalMissionException(ErrorCode.ALREADY_EXISTS_MISSION);
            }
            if (personalMissionInProgress.getMission().getDayCategory() == mission.getDayCategory()) {
                throw new PersonalMissionException(ErrorCode.ALREADY_EXISTS_SAME_DAY_MISSION);
            }
        }

        PersonalMission personalMission = PersonalMission.builder()
                .user(user)
                .mission(mission)
                .build();
        personalMissionRepository.save(personalMission);
        return new PersonalMissionSaveResponseDto(personalMission);
    }

    public PersonalMissionDetailResponseDto findPersonalMissionDetails(Long personalMissionId, Long userId) {
        PersonalMission personalMission = personalMissionRepository.findDetailsById(personalMissionId)
                .orElseThrow(() -> new PersonalMissionException(ErrorCode.INVALID_PERSONAL_MISSION));
        if (personalMission.getUser().getId() != userId) {
            throw new UserException(ErrorCode.INVALID_USER_ACCESS);
        }
        return new PersonalMissionDetailResponseDto(personalMission);
    }

    @Transactional
    public PersonalMissionDeleteResponseDto personalMissionDelete(Long personalMissionId, Long userId) {
        PersonalMission personalMission = findByPersonalMissionId(personalMissionId);
        User user = findByUserId(userId);
        if (!personalMission.getUser().equals(user)) {
            throw new PersonalMissionException(ErrorCode.INVALID_USER_ACCESS);
        }
        personalMissionRepository.deleteById(personalMissionId);
        return new PersonalMissionDeleteResponseDto(personalMission);
    }

    public Page<InProgressResponseDto> getPersonalMissionInProgress(Long userId, Pageable pageable) {
        User user = findByUserId(userId);
        return personalMissionRepository.findAllByUser(user, pageable)
                .map(m -> new InProgressResponseDto(m, howManyPeopleInMission(m)));
    }

    private Long howManyPeopleInMission(PersonalMission personalMission) {
        return personalMissionRepository.findAllByMission(personalMission.getMission()).stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .count();
    }

    @Transactional
    public void changeRemainPeriod() {
        List<PersonalMission> byPersonalMissionStatusEquals = personalMissionRepository.findByPersonalMissionStatusEquals(PersonalMissionStatus.IN_PROGRESS);
        for (PersonalMission personalMission : byPersonalMissionStatusEquals) {
            personalMission.changeRemainPeriod();
        }
    }

    private User findByUserId(Long userId) {
        return userRepository.findByIdFetch(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
    }

    private PersonalMission findByPersonalMissionId(Long id) {
        return personalMissionRepository.findById(id)
                .orElseThrow(() -> new PersonalMissionException(ErrorCode.INVALID_PERSONAL_MISSION));
    }
}
