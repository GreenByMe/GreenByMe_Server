package org.greenbyme.angelhack.domain.missionInfo;

import java.util.Optional;

public interface MissionInfoJPQL {

    Optional<MissionInfo> findDetailsById(Long id);

    Long findProgressByMissionId(Long id);
}
