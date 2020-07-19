package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionRepository;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoRepository;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoStatus;
import org.greenbyme.angelhack.domain.post.PostRepository;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.dto.missionInfo.InProgressResponseDto;
import org.greenbyme.angelhack.service.dto.page.CertPageDto;
import org.greenbyme.angelhack.service.dto.page.HomePageDto;
import org.greenbyme.angelhack.service.dto.page.PopularMissionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PageService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MissionRepository missionRepository;
    private final MissionInfoRepository missionInfoRepository;

    public HomePageDto getHompeageInfos(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
        List<MissionInfo> res = missionInfoRepository.findAllByUser(user);
        long missionCount = res.stream()
                .filter(m -> m.getMissionInfoStatus().equals(MissionInfoStatus.IN_PROGRESS))
                .count();
        long missionProgressCount = postRepository.findAllByUser(user).stream()
                .filter(p -> p.getCreatedDate().getDayOfYear() == LocalDateTime.now().getDayOfYear())
                .count();
        List<InProgressResponseDto> inProgressResponseDtos = missionInfoRepository.findAllByUser(user).stream()
                .filter(m -> m.getMissionInfoStatus().equals(MissionInfoStatus.IN_PROGRESS))
                .map(m -> new InProgressResponseDto(m, howManyPeopleInMission(m)))
                .collect(Collectors.toList());
        Page<PopularMissionResponseDto> popularMissionResponseDtos = missionRepository.findAll(pageable).map(m -> new PopularMissionResponseDto(m, howManyPeopleInMission(m)));
        if (missionCount == 0 || missionProgressCount == 0) {
            return new HomePageDto(user, 0, inProgressResponseDtos, popularMissionResponseDtos);
        }
        long missionProgressRates = (long)( (double)(missionProgressCount /missionCount) * 100);
        return new HomePageDto(user, missionProgressRates, inProgressResponseDtos, popularMissionResponseDtos);
    }

    private Long howManyPeopleInMission(MissionInfo missionInfo) {
        return missionInfoRepository.findAllByMission(missionInfo.getMission()).stream()
                .filter(m -> m.getMissionInfoStatus().equals(MissionInfoStatus.IN_PROGRESS))
                .count();
    }

    private Long howManyPeopleInMission(Mission mission) {
        return missionInfoRepository.findAllByMission(mission).stream()
                .filter(m -> m.getMissionInfoStatus().equals(MissionInfoStatus.IN_PROGRESS))
                .count();
    }

    public CertPageDto getCertPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
        List<MissionInfo> missionInfos = missionInfoRepository.findAllByUser(user);
        return new CertPageDto(userId, missionInfos);
    }
}
