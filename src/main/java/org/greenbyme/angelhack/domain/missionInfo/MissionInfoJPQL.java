package org.greenbyme.angelhack.domain.missionInfo;

import java.util.List;
import java.util.Optional;

public interface MissionInfoJPQL {

    Optional<MissionInfo> findDetailsById(Long id);
    Long findProgressByMissionId(Long missionId);

    List<MissionInfo> findMissionInfoByUserIdAndWhereInProgress(Long userId);
}
