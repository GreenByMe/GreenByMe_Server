package org.greenbyme.angelhack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.controller.UserController;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.PeriodScheduler;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;
import org.greenbyme.angelhack.domain.mission.MissionCertificationMethod;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.UserService;
import org.greenbyme.angelhack.service.dto.user.UserResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserSaveRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDB {

    private static final Logger logger = LoggerFactory.getLogger(InitDB.class);

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
     static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1(){
            User testUser = User.builder()
                    .name("김민석")
                    .email("test")
                    .password(passwordEncoder.encode("test"))
                    .nickname("민석굴암")
                    .photo("https://cafecube.iptime.org/api/users/images/e1dc9a1d-215a-4767-b813-d5c10aca3579_testUser.jpg")
                    .build();
            em.persist(testUser);
            logger.info("testUser: {} 저장 완료", testUser.getName());

            MissionCertificationMethod disposable = MissionCertificationMethod.builder()
                    .title("인증 방법")
                    .text("오늘 드신 식사자리의 수저를 찍어 올려주세요")
                    .missionCertificateCount(MissionCertificateCount.ONE_DAY)
                    .build();
            Mission mission1 = Mission.builder()
                    .category(Category.DISPOSABLE)
                    .subject("일회용 수저를 줄여보아요")
                    .title("오늘은 일회용품을 아껴보는 건 어떨까요?")
                    .description("하루에 버려지는 일회용품 양이 얼마인지 아시나요? 우리가 나서서 일회용품을 줄여 보아요 ")
                    .dayCategory(DayCategory.DAY)
                    .missionCertificationMethod(disposable)
                    .expectCo2(1)
                    .pictureUrl("https://cafecube.iptime.org/api/missions/images/0677c01f-0f3b-4aea-b449-380852cf8b1e_%EC%9D%BC%ED%9A%8C%EC%9A%A9%ED%92%88%20%EB%AF%B8%EC%85%981.PNG")
                    .build();
            em.persist(mission1);
            logger.info("mission1: {} 저장완료", mission1.getSubject());

            MissionCertificationMethod energy = MissionCertificationMethod.builder()
                    .title("인증 방법")
                    .text("야외에서 자전거의 사진을 찍어주세요")
                    .missionCertificateCount(MissionCertificateCount.THREE_TIMES_A_WEEK)
                    .build();
            Mission mission2 = Mission.builder()
                    .category(Category.ENERGY)
                    .subject("자전거를 이용해보아요")
                    .title("이번 주는 자전거로 이동해보는 것 어떨까요?")
                    .description("왜 자전거인가요? <br> 자전거는 도보와 마찬가지로 이동할 때 <br>오염물질을 전혀 배출하지 않아요 ")
                    .dayCategory(DayCategory.WEEK)
                    .missionCertificationMethod(energy)
                    .expectCo2(1.6)
                    .pictureUrl("https://cafecube.iptime.org/api/missions/images/2610c808-2377-4ea4-94f1-3f15263cb6a6_%EC%97%90%EB%84%88%EC%A7%80%EC%A4%84%EC%9D%B4%EA%B8%B0%20%EB%AF%B8%EC%85%981.PNG")
                    .build();
            em.persist(mission2);
            logger.info("mission2: {} 저장완료", mission2.getSubject());

            MissionCertificationMethod traffic = MissionCertificationMethod.builder()
                    .title("인증 방법")
                    .text("정류장에서 버스의 사진을 찍어주세요")
                    .missionCertificateCount(MissionCertificateCount.THREE_TIMES_A_WEEK_MONTH)
                    .build();
            Mission mission3 = Mission.builder()
                    .category(Category.TRAFFIC)
                    .subject("버스를 이용해보아요")
                    .title("이번 달은 버스로 이동해보는 것  어떨까요?")
                    .description("왜 버스인가요? <br> 자동차보다 매연 감소율이 어마무시하답니다.")
                    .dayCategory(DayCategory.MONTH)
                    .missionCertificationMethod(traffic)
                    .expectCo2(6)
                    .pictureUrl("https://cafecube.iptime.org/api/missions/images/08e645ce-4ac4-4134-9099-3c2b36a31252_%EA%B5%90%ED%86%B5%20%EB%AF%B8%EC%85%981.PNG")
                    .build();
            em.persist(mission3);
            logger.info("mission3: {} 저장완료", mission3.getSubject());
        }
    }
}
