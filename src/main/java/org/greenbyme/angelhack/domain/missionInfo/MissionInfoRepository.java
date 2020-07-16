package org.greenbyme.angelhack.domain.missionInfo;

import org.greenbyme.angelhack.domain.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionInfoRepository extends JpaRepository<MissionInfo, Long> {

    List<MissionInfo> findAllByMission(Mission mission);
}
