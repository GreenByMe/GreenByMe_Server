package org.greenbyme.angelhack.domain.missionInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.baseEntity.BaseTimeEntity;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.user.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MissionInfo extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "missioninfo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    private int max;
    private int current;
    private int remainPeriod;

    @Builder
    public MissionInfo(User user, Mission mission){
        setUser(user);
        this.mission = mission;
        this.max=mission.getDayCategory().getDay();
        this.remainPeriod=mission.getDayCategory().getDay();
        this.current = 1;
    }

    public void reduceRemainPeriod(){
        this.remainPeriod--;
    }

    public void addCurrent(){
        this.current++;
    }

    private void setUser(User user) {
        this.user = user;
        user.getMissionInfoList().add(this);
    }
}
