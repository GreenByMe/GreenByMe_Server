package org.greenbyme.angelhack.domain.missionInfo;

import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MissionInfoRepository extends JpaRepository<MissionInfo, Long>, MissionInfoJPQL {

    List<MissionInfo> findAllByMission(Mission mission);

    List<MissionInfo> findByMission(Mission mission);

    List<MissionInfo> findAllByUser(User user);

    List<MissionInfo> findMissionInfoByUserIdAndMissionId(Long userId, Long missionId);
}
