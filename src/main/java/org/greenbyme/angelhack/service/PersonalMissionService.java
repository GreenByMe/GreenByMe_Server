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
import org.greenbyme.angelhack.exception.PersonalMissionException;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.dto.personalmission.InProgressResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDeleteResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDetailResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionSaveResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonalMissionService {

    private final PersonalMissionRepository personalMissionRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public PersonalMissionSaveResponseDto save(Long userId, Long missionId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoResultException("존재하지 않는 사용자입니다."));
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new NoResultException("존재하지 않는 미션입니다."));

        List<PersonalMission> personalMissionByUserIdAndPersonalMissionId = personalMissionRepository.findPersonalMissionByUserIdAndMissionId(userId, missionId);
        for (PersonalMission personalMission : personalMissionByUserIdAndPersonalMissionId) {
            if(personalMission.getPersonalMissionStatus()== PersonalMissionStatus.IN_PROGRESS){
                throw new PersonalMissionException(ErrorCode.ALREADY_EXISTS_MISSION);
            }
        }
        List<PersonalMission> personalMissionByUserIdAndWhereInProgresses = personalMissionRepository.findPersonalMissionByUserIdAndWhereInProgress(userId);

        for (PersonalMission infoByUserIdAndWhereInProgress : personalMissionByUserIdAndWhereInProgresses) {
            if(infoByUserIdAndWhereInProgress.getMission().getDayCategory()==mission.getDayCategory()){
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

    public PersonalMissionDetailResponseDto findPersonalMissionDetails(Long id) {
        PersonalMission personalMission = personalMissionRepository.findDetailsById(id).orElseThrow(() -> new NoResultException("등록되지 않은 미션입니다."));
        return new PersonalMissionDetailResponseDto(personalMission);
    }

    @Transactional
    public PersonalMissionDeleteResponseDto personalMissionDelete(Long id) {
        PersonalMission personalMission = personalMissionRepository.findById(id).orElseThrow(() -> new NoResultException("등록되지 않은 미션 정보입니다."));
        personalMissionRepository.deleteById(id);
        return new PersonalMissionDeleteResponseDto(personalMission);
    }

    public List<InProgressResponseDto> getPersonalMissionInProgress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
        return personalMissionRepository.findAllByUser(user).stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .map(m -> new InProgressResponseDto(m, howManyPeopleInMission(m)))
                .collect(Collectors.toList());
    }

    private Long howManyPeopleInMission(PersonalMission personalMission) {
        return personalMissionRepository.findAllByMission(personalMission.getMission()).stream()
                .filter(m->m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .count();
    }

    @Transactional
    public void changeRemainPeriod(){
        List<PersonalMission> byPersonalMissionStatusEquals = personalMissionRepository.findByPersonalMissionStatusEquals(PersonalMissionStatus.IN_PROGRESS);
        for (PersonalMission personalMission : byPersonalMissionStatusEquals) {
            personalMission.changeRemainPeriod();
        }
    }
}
