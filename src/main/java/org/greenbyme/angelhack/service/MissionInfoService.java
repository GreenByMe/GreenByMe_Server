package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionRepository;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoRepository;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoSaveResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

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

        return new MissionInfoSaveResponseDto(missionInfo);
    }




}
