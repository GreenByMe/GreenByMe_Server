package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.baseEntity.BaseTimeEntity;
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
import org.greenbyme.angelhack.service.dto.page.UserHomePageDetailDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
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
        long missionProgressRates = 0L;

        List<PersonalMission> personalMissionList = personalMissionRepository.findAllByUserIdFetch(userId);
        long missionCount = personalMissionList.stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .mapToLong(PersonalMission::getFinishCount)
                .sum();
        long progressCampaign = personalMissionList.stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .mapToLong(PersonalMission::getProgress)
                .sum();
        if (progressCampaign != 0 && missionCount != 0) {
            missionProgressRates = (long) (((double) progressCampaign / missionCount) * 100);
        }
        UserHomePageDetailDto userHomePageDetailDto = new UserHomePageDetailDto(user, progressCampaign, missionProgressRates);

        List<PersonalMissionByPageDto> personalMissionHomePageDtos = personalMissionList.stream()
                .filter(pm -> pm.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .map(pm -> new PersonalMissionByPageDto(pm, personalMissionRepository.countHowManyPeopleInMission(pm.getMission().getId())))
                .sorted()
                .collect(Collectors.toList());

        int resultSize = 5 - personalMissionHomePageDtos.size();

        personalMissionList.stream()
                .filter(pm -> !pm.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .sorted(Comparator.comparing(BaseTimeEntity::getLastModifiedDate).reversed())
                .limit(resultSize)
                .forEach(pm ->
                        personalMissionHomePageDtos.add(new PersonalMissionByPageDto(pm, personalMissionRepository.countHowManyPeopleInMission(pm.getMission().getId()))));

        List<PopularMissionHomePageResponseDto> popularMissionDtos = missionRepository.findPopularMission();

        return new HomePageDto(userHomePageDetailDto, personalMissionHomePageDtos, popularMissionDtos);
    }

    public CertPageDto getCertPage(Long userId) {
        List<PersonalMission> personalMissions = personalMissionRepository.findInProgressPersonalMissionsByUserId(userId);
        List<PersonalMission> personalMissionByUserIdWithCertification = personalMissionRepository.findInProgressPersonalMissionByUserIdWithCertification(userId);
        List<Long> personalMissionIds = personalMissionByUserIdWithCertification.stream()
                .map(PersonalMission::getId)
                .collect(Collectors.toList());
        List<ProgressPersonalMissionDto> progressPersonalMissionDtos = personalMissions.stream()
                .map(p -> new ProgressPersonalMissionDto(p, isAbleToCertificate(p.getId(), personalMissionIds)))
                .collect(Collectors.toList());
        return new CertPageDto(userId, progressPersonalMissionDtos);
    }

    private boolean isAbleToCertificate(Long id, List<Long> ids) {
        return !ids.contains(id);
    }

    public MyPageDto getMyPage(Long userId) {
        User user = findByUserId(userId);
        long passMissionCount = personalMissionRepository.findAllByUserIdFetch(user.getId()).stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.FINISH))
                .count();
        List<Post> posts = postRepository.findAllByUserId(user.getId());
        return new MyPageDto(user, passMissionCount, posts);
    }

    private User findByUserId(Long userId) {
        return userRepository.findByIdFetch(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
    }
}
