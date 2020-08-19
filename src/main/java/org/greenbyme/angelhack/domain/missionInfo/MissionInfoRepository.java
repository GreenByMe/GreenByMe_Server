package org.greenbyme.angelhack.domain.missionInfo;

import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionInfoRepository extends JpaRepository<MissionInfo, Long>, MissionInfoJPQL {

    List<MissionInfo> findAllByMission(Mission mission);

    List<MissionInfo> findByMission(Mission mission);

    List<MissionInfo> findAllByUser(User user);

    @Query("select m from MissionInfo m where m.missionInfoStatus = 'IN_PROGRESS'")
    Page<MissionInfo> findAllByUser(User user, Pageable pageable);

    List<MissionInfo> findMissionInfoByUserIdAndMissionId(Long userId, Long missionId);

    List<MissionInfo> findByMissionInfoStatusEquals(MissionInfoStatus inProgress);
}
