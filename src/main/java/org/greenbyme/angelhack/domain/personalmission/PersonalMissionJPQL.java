package org.greenbyme.angelhack.domain.personalmission;

import java.util.List;
import java.util.Optional;

public interface PersonalMissionJPQL {

    Optional<PersonalMission> findDetailsById(Long id);

    Long findProgressByMissionId(Long missionId);

    List<PersonalMission> findPersonalMissionByUserIdAndWhereInProgress(Long userId);
}
