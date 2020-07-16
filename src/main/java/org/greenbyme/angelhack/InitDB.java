package org.greenbyme.angelhack;

import lombok.RequiredArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

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

        public void dbInit1(){
            User user1 = User.builder()
                    .name("userA")
                    .nickname("A")
                    .email("aaa@aaa.aaa")
                    .password("aaa")
                    .build();
            em.persist(user1);

            User user2 = User.builder()
                    .name("userB")
                    .nickname("B")
                    .email("bbb@bbb.bbb")
                    .password("bbb")
                    .build();
            em.persist(user2);

            Mission mission2 = Mission.builder()
                    .category(Category.ENERGY)
                    .subject("외출 전 콘센트 불끄기")
                    .description("외출전 콘센트의 불을 끕니다. ")
                    .dayCategory(DayCategory.DAY)
                    .build();
            em.persist(mission2);

            Mission mission1 = Mission.builder()
                    .category(Category.DISPOSABLE)
                    .subject("일회용 젓가락 안쓰기")
                    .description("하루에 한번 일회용 젓가락을 안쓴 모습을 사진으로 남깁니다.")
                    .dayCategory(DayCategory.WEEK)
                    .build();
            em.persist(mission1);

            Mission mission3 = Mission.builder()
                    .category(Category.ENERGY)
                    .subject("하루에 물한잔")
                    .description("음료수보다 물을 먹는게 건강에 더 좋다고 합니다. 하루에 한번 물한잔을 먹은 것을 인증해주세요")
                    .dayCategory(DayCategory.MONTH)
                    .build();
            em.persist(mission3);

            MissionInfo missionInfo1 = MissionInfo.builder()
                    .user(user1)
                    .mission(mission1)
                    .build();
            em.persist(missionInfo1);

            MissionInfo missionInfo2 = MissionInfo.builder()
                    .user(user1)
                    .mission(mission3)
                    .build();
            em.persist(missionInfo2);

            MissionInfo missionInfo3 = MissionInfo.builder()
                    .user(user2)
                    .mission(mission2)
                    .build();
            em.persist(missionInfo3);

            MissionInfo missionInfo4 = MissionInfo.builder()
                    .user(user2)
                    .mission(mission3)
                    .build();
            em.persist(missionInfo4);

        }
    }
}
