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
import org.greenbyme.angelhack.service.dto.personalmission.InProgressResponseDto;
import org.greenbyme.angelhack.service.dto.page.CertPageDto;
import org.greenbyme.angelhack.service.dto.page.HomePageDto;
import org.greenbyme.angelhack.service.dto.page.MyPageDto;
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
    private final PersonalMissionRepository personalMissionRepository;

    public HomePageDto getHompeageInfos(Long userId) {
        User user = findByUserId(userId);
        List<PersonalMission> res = personalMissionRepository.findAllByUser(user);
        long missionCount = res.stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .count();
        long missionProgressCount = postRepository.findAllByUser(user).stream()
                .filter(p -> p.getCreatedDate().getDayOfYear() == LocalDateTime.now().getDayOfYear())
                .count();
        List<InProgressResponseDto> inProgressResponseDtos = res.stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .map(m -> new InProgressResponseDto(m, howManyPeopleInMission(m)))
                .collect(Collectors.toList());
        List<PopularMissionResponseDto> popularMissionResponseDtos = missionRepository.findAll().stream()
                .map(m -> new PopularMissionResponseDto(m, howManyPeopleInMission(m)))
                .collect(Collectors.toList());
        if (missionCount == 0 || missionProgressCount == 0) {
            return new HomePageDto(user, 0, inProgressResponseDtos, popularMissionResponseDtos);
        }
        long missionProgressRates = (long) ((double) (missionProgressCount / missionCount) * 100);
        return new HomePageDto(user, missionProgressRates, inProgressResponseDtos, popularMissionResponseDtos);
    }

    private Long howManyPeopleInMission(PersonalMission personalMission) {
        return personalMissionRepository.findAllByMission(personalMission.getMission()).stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .count();
    }

    private Long howManyPeopleInMission(Mission mission) {
        return personalMissionRepository.findAllByMission(mission).stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .count();
    }

    public CertPageDto getCertPage(Long userId) {
        User user = findByUserId(userId);
        List<PersonalMission> personalMissions = personalMissionRepository.findAllByUser(user);
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
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
    }
}
