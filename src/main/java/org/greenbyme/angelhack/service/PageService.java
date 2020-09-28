package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionRepository;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionRepository;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionStatus;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.post.PostRepository;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.dto.page.*;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionByPageDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionHomePageDto;
import org.greenbyme.angelhack.service.dto.user.UserHomePageDetailDto;
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
    private final PersonalMissionRepository personalMissionRepository;

    public HomePageDto getHomePageInfos(Long userId) {
        User user = findByUserId(userId);
        long missionProgressRates=0L;

        List<PersonalMission> personalMissionList = personalMissionRepository.findAllByUserIdFetch(userId);
        long missionCount = personalMissionList.stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .mapToLong( m -> m.getFinishCount())
                .sum();
        long missionProgressCount = personalMissionList.stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .mapToLong(m -> m.getProgress())
                .sum();
        if (missionProgressCount == 0 && missionCount == 0) {
             missionProgressRates = 0L;
        } else {
             missionProgressRates = (long) (((double) missionProgressCount / missionCount) * 100);
        }
        UserHomePageDetailDto userHomePageDetailDto = new UserHomePageDetailDto(user, missionProgressRates);

        List<PersonalMission> inProgressPersonalMissions = personalMissionList.stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .collect(Collectors.toList());
        List<PersonalMissionHomePageDto> personalMissionHomePageDtos = inProgressPersonalMissions.stream()
                .map(pm -> new PersonalMissionHomePageDto(pm, personalMissionRepository.countHowManyPeopleInMission(pm.getMission().getId())))
                .collect(Collectors.toList());

        List<PopularMissionHomePageResponseDto> popularMissionDtos = missionRepository.findPopularMission();

        return new HomePageDto(userHomePageDetailDto, personalMissionHomePageDtos, popularMissionDtos);
    }

    public CertPageDto getCertPage(Long userId) {
        List<PersonalMission> personalMissions = personalMissionRepository.findAllByUserIdFetch(userId);
        return new CertPageDto(userId, personalMissions);
    }

    public MyPageDto getMyPage(Long userId) {
        User user = findByUserId(userId);
        long passMissionCount = personalMissionRepository.findAllByUser(user).stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.FINISH))
                .count();
        List<Post> posts = postRepository.findAllByUser(user);
        return new MyPageDto(user, passMissionCount, posts);
    }

    private User findByUserId(Long userId) {
        return userRepository.findByIdFetch(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
    }
}
