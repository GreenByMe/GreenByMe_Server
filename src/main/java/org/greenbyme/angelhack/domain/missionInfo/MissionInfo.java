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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MissionInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "missioninfo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated
    private MissionInfoStatus missionInfoStatus;

    private int finishCount;
    private int progress;
    private int remainPeriod;

    @Builder
    public MissionInfo(User user, Mission mission){
        setUser(user);
        this.mission = mission;
        this.finishCount =mission.getMissionCertificationMethod().getMissionCertificateCount().getCount();
        this.remainPeriod=mission.getDayCategory().getDay();
        this.progress = 0;
        this.missionInfoStatus = MissionInfoStatus.IN_PROGRESS;
    }

    public void reduceRemainPeriod(){
        this.remainPeriod--;
    }

    public void changeToFinishStatus(){
        this.missionInfoStatus = MissionInfoStatus.FINISH;
    }

    public void addProgress(){
        if( this.progress >= finishCount) {
            throw new MissionException(ErrorCode.OVER_PROGRESS);
        }
        this.progress++;
    }

    private void setUser(User user) {
        this.user = user;
        user.getMissionInfoList().add(this);
    }
}
