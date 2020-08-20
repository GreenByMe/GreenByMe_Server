package org.greenbyme.angelhack.domain.personalmission;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.baseEntity.BaseTimeEntity;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.MissionException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PersonalMission extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personalmission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated(EnumType.STRING)
    private PersonalMissionStatus personalMissionStatus;

    private int finishCount;
    private int progress;

    @Embedded
    private RemainPeriod remainPeriod;

    @Builder
    public PersonalMission(User user, Mission mission){
        setUser(user);
        this.mission = mission;
        this.finishCount =mission.getMissionCertificationMethod().getMissionCertificateCount().getCount();
        this.remainPeriod = new RemainPeriod((long)mission.getDayCategory().getDay(),0L, 0L);
        this.progress = 0;
        this.personalMissionStatus = PersonalMissionStatus.IN_PROGRESS;
    }

    public void changeRemainPeriod(){
        remainPeriod.setRemainDay(LocalDateTime.now().until(getCreatedDate().plusDays(getMission().getDayCategory().getDay()), ChronoUnit.DAYS));
        remainPeriod.setRemainHour(LocalDateTime.now().until(getCreatedDate().plusDays(getMission().getDayCategory().getDay()), ChronoUnit.HOURS)%24);
        remainPeriod.setRemainMin(LocalDateTime.now().until(getCreatedDate().plusDays(getMission().getDayCategory().getDay()), ChronoUnit.MINUTES)%60);

        if(remainPeriod.getRemainDay()==0 &&remainPeriod.getRemainHour()==0 && remainPeriod.getRemainMin()==0){
            changeToFailStatus();
        }
    }

    private void changeToFinishStatus(){
        this.personalMissionStatus = PersonalMissionStatus.FINISH;
    }

    private void changeToFailStatus() {this.personalMissionStatus = PersonalMissionStatus.FAIL;}

    public void addProgress(){
        if (this.progress >= finishCount || this.personalMissionStatus.equals(PersonalMissionStatus.FINISH)) {
            throw new MissionException(ErrorCode.OVER_PROGRESS);
        }
        if (this.progress + 1 == finishCount) {
            changeToFinishStatus();
        }
        this.progress += 1;
    }

    private void setUser(User user) {
        this.user = user;
        user.getPersonalMissionList().add(this);
    }

    public boolean isEnd() {
        return this.personalMissionStatus.equals(PersonalMissionStatus.FINISH);
    }
}
