package org.greenbyme.angelhack.domain.missionInfo;

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
public class MissionInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "missioninfo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated(EnumType.STRING)
    private MissionInfoStatus missionInfoStatus;

    private int finishCount;
    private int progress;

    @Embedded
    private RemainPeriod remainPeriod;

    @Builder
    public MissionInfo(User user, Mission mission){
        setUser(user);
        this.mission = mission;
        this.finishCount =mission.getMissionCertificationMethod().getMissionCertificateCount().getCount();
        this.remainPeriod = new RemainPeriod((long)mission.getDayCategory().getDay(),0L, 0L);
        this.progress = 0;
        this.missionInfoStatus = MissionInfoStatus.IN_PROGRESS;
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
        this.missionInfoStatus = MissionInfoStatus.FINISH;
    }

    private void changeToFailStatus() {this.missionInfoStatus = MissionInfoStatus.FAIL;}

    public void addProgress(){
        if (this.progress >= finishCount || this.missionInfoStatus.equals(MissionInfoStatus.FINISH)) {
            throw new MissionException(ErrorCode.OVER_PROGRESS);
        }
        if (this.progress + 1 == finishCount) {
            changeToFinishStatus();
        }
        this.progress += 1;
    }

    private void setUser(User user) {
        this.user = user;
        user.getMissionInfoList().add(this);
    }

    public boolean isEnd() {
        return this.missionInfoStatus.equals(MissionInfoStatus.FINISH);
    }
}
