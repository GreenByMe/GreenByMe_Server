package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionRepository;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoRepository;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoStatus;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.dto.missionInfo.InProgressResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoDeleteResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoDetailResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoSaveResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MissionInfoService {

    private final MissionInfoRepository missionInfoRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public MissionInfoSaveResponseDto save(Long userId, Long missionId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoResultException("존재하지 않는 사용자입니다."));
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new NoResultException("존재하지 않는 미션입니다."));

        MissionInfo missionInfo = MissionInfo.builder()
                .user(user)
                .mission(mission)
                .build();
        missionInfoRepository.save(missionInfo);
        mission.addCandidates();
        return new MissionInfoSaveResponseDto(missionInfo);
    }

    public MissionInfoDetailResponseDto findMissionInfoDetails(Long id) {
        MissionInfo missionInfo = missionInfoRepository.findDetailsById(id).orElseThrow(() -> new NoResultException("등록되지 않은 미션입니다."));
        return new MissionInfoDetailResponseDto(missionInfo);
    }

    @Transactional
    public MissionInfoDeleteResponseDto missionInfoDelete(Long id) {
        MissionInfo missionInfo = missionInfoRepository.findById(id).orElseThrow(() -> new NoResultException("등록되지 않은 미션 정보입니다."));
        missionInfoRepository.deleteById(id);
        return new MissionInfoDeleteResponseDto(missionInfo);
    }

    public List<InProgressResponseDto> getMissionInfoInProgress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
        return missionInfoRepository.findAllByUser(user).stream()
                .filter(m->m.getMissionInfoStatus().equals(MissionInfoStatus.IN_PROGRESS))
                .map(InProgressResponseDto::new)
                .collect(Collectors.toList());
    }
}
